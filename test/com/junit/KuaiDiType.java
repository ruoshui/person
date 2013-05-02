package com.junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class KuaiDiType {
	private static Map map = null;
	private static List list = new ArrayList<String>();

	/**
	 * 获取单例
	 * 
	 * @return map
	 */

	public static Map<String, String> getMap() {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		if (map.size() == 0) {
			insertKuaiDiType();
		}
		return map;
	}

	public static List getList() {
		return list;
	}

	private static void insertKuaiDiType() {
		if (!list.contains("ems")) {
			list.add("ems");
			map.put("ems", "EMS");
		}
		if (!list.contains("shentong")) {
			list.add("shentong");
			map.put("shentong", "申�?");
		}
		if (!list.contains("zhongtong")) {
			list.add("zhongtong");
			map.put("zhongtong", "中�?");
		}
		if (!list.contains("yunda")) {
			list.add("yunda");
			map.put("yunda", "韵达");
		}
		if (!list.contains("zhaijisong")) {
			list.add("zhaijisong");
			map.put("zhaijisong", "宅�?�?);
		}
		if (!list.contains("youzhengguonei")) {
			list.add("youzhengguonei");
			map.put("youzhengguonei", "邮政包裹");
		}
		if (!list.contains("shunfeng")) {
			list.add("shunfeng");
			map.put("shunfeng", "顺丰");
		}
		if (!list.contains("yuantong")) {
			list.add("yuantong");
			map.put("yuantong", "圆�?");
		}
		if (!list.contains("huitongkuaidi")) {
			list.add("huitongkuaidi");
			map.put("huitongkuaidi", "汇�?");
		}
		if (!list.contains("tiantian")) {
			list.add("tiantian");
			map.put("tiantian", "天天");
		}
		if (!list.contains("quanfengkuaidi")) {
			list.add("quanfengkuaidi");
			map.put("quanfengkuaidi", "全峰");
		}
		if (!list.contains("rufengda")) {
			list.add("rufengda");
			map.put("rufengda", "凡客配�?");
		}
		if (!list.contains("lianbangkuaidi")) {
			list.add("lianbangkuaidi");
			map.put("lianbangkuaidi", "联邦快�?");
		}
		if (!list.contains("lianhaowuliu")) {
			list.add("lianhaowuliu");
			map.put("lianhaowuliu", "联昊�?);
		}
		if (!list.contains("quanfengkuaidi")) {
			list.add("quanfengkuaidi");
			map.put("quanfengkuaidi", "全峰快�?");
		}
		if (!list.contains("quanchenkuaidi")) {
			list.add("quanchenkuaidi");
			map.put("quanchenkuaidi", "全晨快�?");
		}
		if (!list.contains("quanyikuaidi")) {
			list.add("quanyikuaidi");
			map.put("quanyikuaidi", "全一快�?");
		}
		if (!list.contains("suer")) {
			list.add("suer");
			map.put("suer", "速尔快�?");
		}
		if (!list.contains("shangcheng")) {
			list.add("shangcheng");
			map.put("shangcheng", "尚橙物流");
		}
		if (!list.contains("tnt")) {
			list.add("tnt");
			map.put("tnt", "TNT");
		}
		if (!list.contains("tiandihuayu")) {
			list.add("tiandihuayu");
			map.put("tiandihuayu", "天地华宇");
		}
		if (!list.contains("usps")) {
			list.add("usps");
			map.put("usps", "USPS");
		}
		if (!list.contains("ups")) {
			list.add("ups");
			map.put("ups", "UPS");
		}
		if (!list.contains("youshuwuliu")) {
			list.add("youshuwuliu");
			map.put("youshuwuliu", "优�?物流");
		}
		if (!list.contains("xinbangwuliu")) {
			list.add("xinbangwuliu");
			map.put("xinbangwuliu", "新邦物流");
		}
		if (!list.contains("xinfengwuliu")) {
			list.add("xinfengwuliu");
			map.put("xinfengwuliu", "信丰物流");
		}
		if (!list.contains("neweggozzo")) {
			list.add("neweggozzo");
			map.put("neweggozzo", "新蛋物流");
		}
		if (!list.contains("youzhengguonei")) {
			list.add("youzhengguonei");
			map.put("youzhengguonei", "邮政国内");
		}
		if (!list.contains("youzhengguoji")) {
			list.add("youzhengguoji");
			map.put("youzhengguoji", "邮政国际");
		}
		if (!list.contains("yinjiesudi")) {
			list.add("yinjiesudi");
			map.put("yinjiesudi", "银捷速�?");
		}
		if (!list.contains("zhongyouwuliu")) {
			list.add("zhongyouwuliu");
			map.put("zhongyouwuliu", "中邮物流");
		}
		if (!list.contains("ztky")) {
			list.add("ztky");
			map.put("ztky", "中铁物流");
		}
		if (!list.contains("zhongtiewuliu")) {
			list.add("zhongtiewuliu");
			map.put("zhongtiewuliu", "中铁快运");
		}
		if (!list.contains("youzhengguonei")) {
			list.add("youzhengguonei");
			map.put("youzhengguonei", "包裹/平邮");
		}
		if (!list.contains("bangsongwuliu")) {
			list.add("bangsongwuliu");
			map.put("bangsongwuliu", "邦�?物流");
		}
		if (!list.contains("dhl")) {
			list.add("dhl");
			map.put("dhl", "DHL");
		}
		if (!list.contains("debangwuliu")) {
			list.add("debangwuliu");
			map.put("debangwuliu", "德邦物流");
		}
		if (!list.contains("disifang")) {
			list.add("disifang");
			map.put("disifang", "递四�?);
		}
		if (!list.contains("ems")) {
			list.add("ems");
			map.put("ems", "E邮宝");
		}
		if (!list.contains("emsguoji")) {
			list.add("emsguoji");
			map.put("emsguoji", "EMS国际");
		}
		if (!list.contains("fedex")) {
			list.add("fedex");
			map.put("fedex", "FedEx");
		}
		if (!list.contains("feibaokuaidi")) {
			list.add("feibaokuaidi");
			map.put("feibaokuaidi", "飞豹快�?");
		}
		if (!list.contains("feikangda")) {
			list.add("feikangda");
			map.put("feikangda", "飞康�?);
		}
		if (!list.contains("youzhengguonei")) {
			list.add("youzhengguonei");
			map.put("youzhengguonei", "挂号�?);
		}
		if (!list.contains("guotongkuaidi")) {
			list.add("guotongkuaidi");
			map.put("guotongkuaidi", "国�?快�?");
		}
		if (!list.contains("ganzhongnengda")) {
			list.add("ganzhongnengda");
			map.put("ganzhongnengda", "能达速�?");
		}
		if (!list.contains("huitongkuaidi")) {
			list.add("huitongkuaidi");
			map.put("huitongkuaidi", "汇�?快运");
		}
		if (!list.contains("huaqikuaiyun")) {
			list.add("huaqikuaiyun");
			map.put("huaqikuaiyun", "华企快运");
		}
		if (!list.contains("huiqiangkuaidi")) {
			list.add("huiqiangkuaidi");
			map.put("huiqiangkuaidi", "汇强快�?");
		}
		if (!list.contains("jiajiwuliu")) {
			list.add("jiajiwuliu");
			map.put("jiajiwuliu", "佳吉快运");
		}
		if (!list.contains("jialidatong")) {
			list.add("jialidatong");
			map.put("jialidatong", "嘉里大�?");
		}
		if (!list.contains("jixianda")) {
			list.add("jixianda");
			map.put("jixianda", "急先�?);
		}
		if (!list.contains("kuaijiesudi")) {
			list.add("kuaijiesudi");
			map.put("kuaijiesudi", "快捷速�?");
		}
		if (!list.contains("kuayue")) {
			list.add("kuayue");
			map.put("kuayue", "跨越速�?");
		}
		if (!list.contains("debangwuliu")) {
			list.add("debangwuliu");
			map.put("debangwuliu", "德邦物流");
		}
		if (!list.contains("tiandihuayu")) {
			list.add("tiandihuayu");
			map.put("tiandihuayu", "天地华宇");
		}
		if (!list.contains("longbanwuliu")) {
			list.add("longbanwuliu");
			map.put("longbanwuliu", "龙邦速�?");
		}
		if (!list.contains("youshuwuliu")) {
			list.add("youshuwuliu");
			map.put("youshuwuliu", "优�?物流");
		}
		if (!list.contains("zhongyouwuliu")) {
			list.add("zhongyouwuliu");
			map.put("zhongyouwuliu", "中邮物流");
		}
		if (!list.contains("chuanxiwuliu")) {
			list.add("chuanxiwuliu");
			map.put("chuanxiwuliu", "传喜物流");
		}
		if (!list.contains("neweggozzo")) {
			list.add("neweggozzo");
			map.put("neweggozzo", "新蛋物流");
		}
		if (!list.contains("feikangda")) {
			list.add("feikangda");
			map.put("feikangda", "飞康�?);
		}
		if (!list.contains("shentong")) {
			list.add("shentong");
			map.put("shentong", "申�?E物流");
		}
		if (!list.contains("shangda")) {
			list.add("shangda");
			map.put("shangda", "上大物流");
		}
		if (!list.contains("shenganwuliu")) {
			list.add("shenganwuliu");
			map.put("shenganwuliu", "圣安物流");
		}
		if (!list.contains("shengfengwuliu")) {
			list.add("shengfengwuliu");
			map.put("shengfengwuliu", "盛丰物流");
		}
		if (!list.contains("shenghuiwuliu")) {
			list.add("shenghuiwuliu");
			map.put("shenghuiwuliu", "盛辉物流");
		}
		if (!list.contains("suijiawuliu")) {
			list.add("suijiawuliu");
			map.put("suijiawuliu", "穗佳物流");
		}
		if (!list.contains("wanxiangwuliu")) {
			list.add("wanxiangwuliu");
			map.put("wanxiangwuliu", "万象物流");
		}
		if (!list.contains("wanjiawuliu")) {
			list.add("wanjiawuliu");
			map.put("wanjiawuliu", "万家物流");
		}
		if (!list.contains("xinbangwuliu")) {
			list.add("xinbangwuliu");
			map.put("xinbangwuliu", "新邦物流");
		}
		if (!list.contains("xinfengwuliu")) {
			list.add("xinfengwuliu");
			map.put("xinfengwuliu", "信丰物流");
		}
		if (!list.contains("neweggozzo")) {
			list.add("neweggozzo");
			map.put("neweggozzo", "新蛋物流");
		}
		if (!list.contains("youshuwuliu")) {
			list.add("youshuwuliu");
			map.put("youshuwuliu", "优�?物流");
		}
		if (!list.contains("yuefengwuliu")) {
			list.add("yuefengwuliu");
			map.put("yuefengwuliu", "越丰物流");
		}
		if (!list.contains("yuanchengwuliu")) {
			list.add("yuanchengwuliu");
			map.put("yuanchengwuliu", "远成物流");
		}
		if (!list.contains("yuntongkuaidi")) {
			list.add("yuntongkuaidi");
			map.put("yuntongkuaidi", "运�?中港");
		}
		if (!list.contains("yuanfeihangwuliu")) {
			list.add("yuanfeihangwuliu");
			map.put("yuanfeihangwuliu", "原飞�?);
		}
		if (!list.contains("zhongyouwuliu")) {
			list.add("zhongyouwuliu");
			map.put("zhongyouwuliu", "中邮物流");
		}
		if (!list.contains("ztky")) {
			list.add("ztky");
			map.put("ztky", "中铁物流");
		}
		if (!list.contains("zhongtianwanyun")) {
			list.add("zhongtianwanyun");
			map.put("zhongtianwanyun", "中天万运");
		}
		if (!list.contains("datianwuliu")) {
			list.add("datianwuliu");
			map.put("datianwuliu", "邦�?物流");
		}
		if (!list.contains("debangwuliu")) {
			list.add("debangwuliu");
			map.put("debangwuliu", "百福东方");
		}
		if (!list.contains("datianwuliu")) {
			list.add("datianwuliu");
			map.put("datianwuliu", "大田物流");
		}
		if (!list.contains("dsukuaidi")) {
			list.add("dsukuaidi");
			map.put("dsukuaidi", "D速物�?);
		}
		if (!list.contains("ganzhongnengda")) {
			list.add("ganzhongnengda");
			map.put("ganzhongnengda", "能达速�?");
		}
		if (!list.contains("gongsuda")) {
			list.add("gongsuda");
			map.put("gongsuda", "共�?�?);
		}
		if (!list.contains("guotongkuaidi")) {
			list.add("guotongkuaidi");
			map.put("guotongkuaidi", "国�?快�?");
		}
		if (!list.contains("hengluwuliu")) {
			list.add("hengluwuliu");
			map.put("hengluwuliu", "恒路物流");
		}
		if (!list.contains("huaxialongwuliu")) {
			list.add("huaxialongwuliu");
			map.put("huaxialongwuliu", "华厦�?);
		}
		if (!list.contains("jialidatong")) {
			list.add("jialidatong");
			map.put("jialidatong", "嘉里大�?");
		}
		if (!list.contains("jiayiwuliu")) {
			list.add("jiayiwuliu");
			map.put("jiayiwuliu", "佳�?物流");
		}
		if (!list.contains("jixianda")) {
			list.add("jixianda");
			map.put("jixianda", "急先�?);
		}
		if (!list.contains("kangliwuliu")) {
			list.add("kangliwuliu");
			map.put("kangliwuliu", "康力物流");
		}
		if (!list.contains("mingliangwuliu")) {
			list.add("mingliangwuliu");
			map.put("mingliangwuliu", "明亮物流");
		}
		if (!list.contains("ems")) {
			list.add("ems");
			map.put("ems", "EMS");
		}
		if (!list.contains("youzhengguonei")) {
			list.add("youzhengguonei");
			map.put("youzhengguonei", "邮政国内");
		}
		if (!list.contains("ups")) {
			list.add("ups");
			map.put("ups", "UPS");
		}
		if (!list.contains("fedex")) {
			list.add("fedex");
			map.put("fedex", "FedEx");
		}
		if (!list.contains("tnt")) {
			list.add("tnt");
			map.put("tnt", "TNT");
		}
		if (!list.contains("emsen")) {
			list.add("emsen");
			map.put("emsen", "EMS国际");
		}
		if (!list.contains("youzhengguoji")) {
			list.add("youzhengguoji");
			map.put("youzhengguoji", "邮政国际");
		}
		if (!list.contains("usps")) {
			list.add("usps");
			map.put("usps", "USPS");
		}
		if (!list.contains("dhl")) {
			list.add("dhl");
			map.put("dhl", "DHL");
		}
		if (!list.contains("ontrac")) {
			list.add("ontrac");
			map.put("ontrac", "OnTrac");
		}
		if (!list.contains("minghangkuaidi")) {
			list.add("minghangkuaidi");
			map.put("minghangkuaidi", "民航快�?");
		}
		if (!list.contains("meiguokuaidi")) {
			list.add("meiguokuaidi");
			map.put("meiguokuaidi", "美国快�?");
		}
		if (!list.contains("ocs")) {
			list.add("ocs");
			map.put("ocs", "OCS");
		}
		if (!list.contains("ontrac")) {
			list.add("ontrac");
			map.put("ontrac", "OnTrac");
		}
		if (!list.contains("shunfengen")) {
			list.add("shunfengen");
			map.put("shunfengen", "顺丰-美国");
		}
		if (!list.contains("santaisudi")) {
			list.add("santaisudi");
			map.put("santaisudi", "三�?速�?");
		}
		if (!list.contains("tnt")) {
			list.add("tnt");
			map.put("tnt", "TNT");
		}
		if (!list.contains("tnten")) {
			list.add("tnten");
			map.put("tnten", "TNT-英文");
		}
		if (!list.contains("usps")) {
			list.add("usps");
			map.put("usps", "USPS");
		}
		if (!list.contains("ups")) {
			list.add("ups");
			map.put("ups", "UPS");
		}
		if (!list.contains("upsen")) {
			list.add("upsen");
			map.put("upsen", "UPS-英文");
		}
		if (!list.contains("youzhengguonei")) {
			list.add("youzhengguonei");
			map.put("youzhengguonei", "邮政国内");
		}
		if (!list.contains("youzhengguoji")) {
			list.add("youzhengguoji");
			map.put("youzhengguoji", "邮政国际");
		}
		if (!list.contains("zhaijisong")) {
			list.add("zhaijisong");
			map.put("zhaijisong", "宅�?�?);
		}
		if (!list.contains("zhongsukuaidi")) {
			list.add("zhongsukuaidi");
			map.put("zhongsukuaidi", "中�?快�?");
		}
		if (!list.contains("aae")) {
			list.add("aae");
			map.put("aae", "AAE-中国");
		}
		if (!list.contains("auspost")) {
			list.add("auspost");
			map.put("auspost", "澳大利亚邮政");
		}
		if (!list.contains("baifudongfang")) {
			list.add("baifudongfang");
			map.put("baifudongfang", "百褔东方");
		}
		if (!list.contains("bht")) {
			list.add("bht");
			map.put("bht", "BHT");
		}
		if (!list.contains("coe")) {
			list.add("coe");
			map.put("coe", "COE");
		}
		if (!list.contains("guotongkuaidi")) {
			list.add("guotongkuaidi");
			map.put("guotongkuaidi", "CCES(国�?)");
		}
		if (!list.contains("dhl")) {
			list.add("dhl");
			map.put("dhl", "DHL-英文");
		}
		if (!list.contains("dhlde")) {
			list.add("dhlde");
			map.put("dhlde", "DHL-德国");
		}
		if (!list.contains("ems")) {
			list.add("ems");
			map.put("ems", "EMS");
		}
		if (!list.contains("emsguoji")) {
			list.add("emsguoji");
			map.put("emsguoji", "EMS-国际");
		}
		if (!list.contains("emsen")) {
			list.add("emsen");
			map.put("emsen", "EMS-英文");
		}
		if (!list.contains("lianbangkuaidi")) {
			list.add("lianbangkuaidi");
			map.put("lianbangkuaidi", "FedEx-国内");
		}
		if (!list.contains("fedex")) {
			list.add("fedex");
			map.put("fedex", "FedEx-国际");
		}
		if (!list.contains("fedexus")) {
			list.add("fedexus");
			map.put("fedexus", "FedEx-美国");
		}
		if (!list.contains("canpost")) {
			list.add("canpost");
			map.put("canpost", "加拿大邮�?);
		}
		if (!list.contains("fedex")) {
			list.add("fedex");
			map.put("fedex", "联邦快�?");
		}

	}
}
