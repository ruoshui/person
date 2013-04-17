package cn.wang.yin.hessian.api;

import java.io.File;
import java.util.List;

import com.wang.yin.hessian.bean.GpsInfo;

public interface Remot {
	// public User Hession(String name);

	public String Test(String name);

	public boolean uploadGps(GpsInfo gps);

	public int uploadGps(List<GpsInfo> listGps);

	public String mm();

	public String saveFile(File file);
}
