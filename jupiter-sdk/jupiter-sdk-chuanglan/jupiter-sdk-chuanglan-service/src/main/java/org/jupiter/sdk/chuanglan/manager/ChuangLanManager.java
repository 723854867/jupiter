package org.jupiter.sdk.chuanglan.manager;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.jupiter.bean.model.query.Condition;
import org.jupiter.bean.model.query.Query;
import org.jupiter.sdk.chuanglan.ChuangLanApi;
import org.jupiter.sdk.chuanglan.LogEnableCondition;
import org.jupiter.sdk.chuanglan.bean.entity.LogSms;
import org.jupiter.sdk.chuanglan.bean.notice.ReporterCommonNotice;
import org.jupiter.sdk.chuanglan.bean.response.QuerySmsResponse.SmsDetail;
import org.jupiter.sdk.chuanglan.mybatis.dao.LogSmsDao;
import org.jupiter.util.lang.DateUtil;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Conditional(value = LogEnableCondition.class)
public class ChuangLanManager {

	@Resource
	private LogSmsDao logSmsDao;
	
	public void logSms(List<LogSms> logs) {
		logSmsDao.insertMany(logs);
	}
	
	public void syncLogSms(List<SmsDetail> details) {
		Set<String> set = new HashSet<String>();
		details.forEach(detail -> set.add(detail.getMsgId()));
		List<LogSms> logs = logSmsDao.queryList(new Query().and(Condition.in("msg_id", set)));
		Iterator<SmsDetail> iterator = details.iterator();
		for (LogSms log : logs) {
			while (iterator.hasNext()) {
				SmsDetail detail = iterator.next();
				if (detail.getMobile().equals(log.getMobile()) && detail.getMsgId().equals(log.getMsgId())) {
					iterator.remove();
					log.setState(detail.getStatus());
					log.setUpdated(DateUtil.current());
				}
			}
		}
		logSmsDao.replaceCollection(logs);
	}
	
	@Transactional
	public void reporterNotice(ReporterCommonNotice notice) { 
		LogSms log = logSmsDao.queryUnique(new Query().and(Condition.eq("msg_id", notice.getMsgid()), Condition.eq("mobile", notice.getMobile())));
		if (null != log) {
			log.setUpdated(DateUtil.current());
			log.setState(ChuangLanApi.state(notice.getStatus()));
			logSmsDao.update(log);
		}
	}
}
