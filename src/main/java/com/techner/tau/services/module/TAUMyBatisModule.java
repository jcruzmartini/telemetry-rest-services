package com.techner.tau.services.module;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.c3p0.C3p0DataSourceProvider;

import com.techner.tau.common.entity.TAUAlert;
import com.techner.tau.common.entity.TAUAlertEvent;
import com.techner.tau.common.entity.TAUAlertRule;
import com.techner.tau.common.entity.TAUDailyReport;
import com.techner.tau.common.entity.TAUDataScale;
import com.techner.tau.common.entity.TAUGraphData;
import com.techner.tau.common.entity.TAUMeasure;
import com.techner.tau.common.entity.TAUMeasureResult;
import com.techner.tau.common.entity.TAUNotification;
import com.techner.tau.common.entity.TAUQueryResult;
import com.techner.tau.common.entity.TAUStationInfo;
import com.techner.tau.common.entity.TAUUser;
import com.techner.tau.common.entity.TAUVariable;
import com.techner.tau.services.mapper.AlertMapper;
import com.techner.tau.services.mapper.EntityMapper;
import com.techner.tau.services.mapper.GraphicsMapper;
import com.techner.tau.services.mapper.MeasuresMapper;
import com.techner.tau.services.mapper.NotificationMapper;
import com.techner.tau.services.mapper.OperationMapper;
import com.techner.tau.services.mapper.QueryMapper;
import com.techner.tau.services.mapper.ReportMapper;
import com.techner.tau.services.mapper.StationMapper;
import com.techner.tau.services.mapper.UserMapper;

public class TAUMyBatisModule extends MyBatisModule {

	@Override
	protected void initialize() {
		bindDataSourceProviderType(C3p0DataSourceProvider.class);
		bindTransactionFactoryType(JdbcTransactionFactory.class);
		addMapperClass(UserMapper.class);
		addMapperClass(MeasuresMapper.class);
		addMapperClass(StationMapper.class);
		addMapperClass(NotificationMapper.class);
		addMapperClass(AlertMapper.class);
		addMapperClass(QueryMapper.class);
		addMapperClass(GraphicsMapper.class);
		addMapperClass(EntityMapper.class);
		addMapperClass(ReportMapper.class);
		addMapperClass(OperationMapper.class);
		addAlias("User").to(TAUUser.class);
		addAlias("Variable").to(TAUVariable.class);
		addAlias("MeasureResult").to(TAUMeasureResult.class);
		addAlias("Measure").to(TAUMeasure.class);
		addAlias("StationInfo").to(TAUStationInfo.class);
		addAlias("Notification").to(TAUNotification.class);
		addAlias("Alert").to(TAUAlert.class);
		addAlias("AlertEvent").to(TAUAlertEvent.class);
		addAlias("AlertRule").to(TAUAlertRule.class);
		addAlias("QueryResult").to(TAUQueryResult.class);
		addAlias("GraphData").to(TAUGraphData.class);
		addAlias("DataScale").to(TAUDataScale.class);
		addAlias("DailyReport").to(TAUDailyReport.class);
	}
}
