package com.junit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class kuaidi {
	static List<File> listFile = new ArrayList();

	public static void main(String[] s) {
		String url = "http://10.851008988.duapp.com/index2.html";
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements content = doc.getElementsByClass("content");
		Element con = content.get(0);
		Elements links = con.getElementsByTag("a");
		for (Element link : links) {
			String onclick = link.attr("onclick");
			String linkText = link.text();
			if (onclick.startsWith("selectCompany")) {
				onclick = onclick.replace("',this);return false;", "");
				onclick = onclick.replace("selectCompany('", "");
			//	System.out.println(onclick + "\t" + linkText);
				System.out.println("if (!list.contains(\"" + onclick + "\")) {");
				System.out.println("list.add(\"" + onclick + "\");");
				System.out.println("map.put(\"" + onclick + "\", \"" + linkText
						+ "\");}");
			}

		}
	}

}
