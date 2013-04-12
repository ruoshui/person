package cn.wang.yin.hessian.api;

public interface ETongReport {
	/**
	 * OBD终端注册
	 * @param mobile	手机号
	 * @param obdno		OBD机身号
	 * @param pwd		登录密码
	 * @param licenseplate		车牌号码
	 * @param vehicletype		车辆类型
	 * @param recentMileage		当前里程
	 * @param beforeMaitainMileage		下次保养里程
	 * @return			成功返回“OK”，失败返回“FAIL”
	 */
	public String registerOBDDevice(String mobile, String obdno, String pwd, String licenseplate, String vehicletype, int recentMileage, 
			int beforeMaitainMileage);
	/**
	 * 登录
	 * @param mobile	手机号
	 * @param pwd		密码
	 * @return			终端ID
	 */
	public String login(String mobile, String pwd);
	
	/**
	 * 登录
	 * @param mobile	手机号
	 * @param pwd		密码
	 * @return			终端ID、车牌号、车辆类型、OBD编号、车主姓名
	 */
	public String loginForVehicleInfo(String mobile, String pwd);
	
	/**
	 * 忘记密码
	 * @param mobile	手机号
	 * @return			成功返回“OK”，失败返回“FAIL”
	 */
	public String forgetPassword(String mobile);
	
	/**
	 * 修改密码
	 * @param mobile	手机号
	 * @param oldPwd	旧密码
	 * @param newPwd	新密码
	 * 					修改成功
	 * @return			成功返回“OK”，失败返回“FAIL”
	 */
	public String modifyPassword(String mobile, String oldPwd, String newPwd);
	
	/**
	 * 获取车辆体检指数
	 * @param deviceid	终端编号
	 * @return
	 */
	public String getHealthIndex(String deviceid);
	
	/**
	 * 获取实时车辆状态信息
	 * @param deviceid	终端编号
	 * @return			车辆状态信息
	 */
	public String getRealtimeOBDData(String deviceid);
	
	/**
	 * 获取所有行程的统计信息
	 * @param deviceid	终端编号
	 * @return			总行程时长、总行程里程、总行程次数、总油耗量
	 */
	public String getAllRouteStatistics(String deviceid);
	
	/**
	 * 获取历史行程信息
	 * @param deviceid	终端编号
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return			行程开始时间、行程结束时间、里程、总油耗、最高速度、最高水温、发动机最高转速、电压、平均油耗、
	 * 					平均速度
	 */
	public String getRouteByTime(String deviceid, String startTime, String endTime);
	
	/**
	 * 获取行程路线信息
	 * @param deviceid	终端编号
	 * @param startTime	开始时间
	 * @param endTime	结束时间
	 * @return			经度、纬度、位置
	 */
	public String getRouteLine(String deviceid, String startTime, String endTime);
	
	/**
	 * 意见反馈
	 * @param mobile	手机号
	 * @param content	反馈内容
	 * @return
	 */
	public String feedback(String mobile, String content);
	
	/**
	 * 获取车辆体检详细信息
	 * @param deviceid	终端编号
	 * @return
	 */
	public String getHealthDetail(String deviceid);

	/**
	 * 收集调试信息的接口
	 * 
	 * @param message
	 *            Exception.getMessage()
	 * @param exceptiontype
	 *            Exception.getClass()
	 * @param exlocation
	 *            错误出现的类位置 com.etgps.etong.*
	 * @param phonenum
	 *            登录的手机号
	 * @param pruducttime
	 *            错误产山时间
	 * @param writetime
	 *            错误提交时间
	 * @param type
	 *    	错误类型 0 ：安卓<br>
	 *            1:IOS
	 * @return 是否提交成功
	 */
	public boolean CelectionDebug(String message,String exceptiontype,String exlocation,String phonenum,String pruducttime,String writetime,int type);
}
