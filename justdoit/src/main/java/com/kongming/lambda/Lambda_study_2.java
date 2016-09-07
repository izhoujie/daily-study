package com.kongming.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lambda_study_2 {
    public static void main(String[] args) {
	String str = "T3_2012_GameResultUser, pants, ProductTrial_UserRequest, T3campaign, T4_picasso_Users, vertical_user, campaign20140318, huggies_t5_hongbao_duijiang, huggies_fetalmove_records, T5trail, huggies_double_eleven, huggies_T3_coupon, EC, Jean_Photos, huggies_pome_coupon, happy40weeks, app, t5hotmom, huggies_invite_code, T3weibo, huggies_invite_coupon_xinsheng, huggies_invite_code_xinsheng, T5apply, message, Hospital_upload, T3_Trial_User, huggies_t3_mingdan, Pants_EDM_Confirm, huggies_invite_code_T3, weibowin, weixin, weixin_user, Pome_UserUpload, PantsLaunch_Log_PlayGame, EC2014, baby_info, huggies_T3_address, Hospital_0128, huggies_t3_prize, huggies_invite_coupon, Kongfu_Photos, customer, T4_UserData";
	String str1 = "weixin,weixin_user,huggies_double_eleven,huggies_fetalmove_records,huggies_invite_code,huggies_invite_code_T3,huggies_invite_code_xinsheng,huggies_invite_coupon,huggies_invite_coupon_xinsheng,huggies_pome_coupon,huggies_T3_address,huggies_T3_coupon,huggies_t3_mingdan,huggies_t3_prize,huggies_t5_hongbao_duijiang,baby_info,vertical_user,campaign20140318,T5trail,t5hotmom,T5apply,T3weibo,T3campaign,pants,message,app,weibowin,customer,T4_UserData,T4_picasso_Users,T3_Trial_User,T3_2012_GameResultUser,ProductTrial_UserRequest,Pome_UserUpload,PantsLaunch_Log_PlayGame,Pants_EDM_Confirm,Kongfu_Photos,Jean_Photos,Hospital_upload,Hospital_0128,happy40weeks,EC,EC2014";
	String[] split = str.split(", ");
	String[] split1 = str1.split(",");
	HashSet<String> set = new HashSet<String>(Arrays.asList(split));
	HashSet<String> set1 = new HashSet<String>(Arrays.asList(split1));

	System.out.println(set.size());
	System.out.println(set1.size());
	set.removeAll(set1);
	System.out.println(set.size());
	System.out.println(split.length);
	for (int i = 0; i < split.length; i++) {
	    System.out.print(split[i] + "  ");
	}

	System.getProperties().setProperty("proxySet", "true");
	System.getProperties().setProperty("http.proxyHost", "192.168.130.15");
	System.getProperties().setProperty("http.proxyPort", "8848");

	System.getProperties().setProperty("Zhou", "Jie");

	Properties properties = System.getProperties();

	Iterator<Entry<Object, Object>> iterator = properties.entrySet().iterator();

	while (iterator.hasNext()) {
	    Entry<java.lang.Object, java.lang.Object> entry = iterator.next();

	    System.out.println(entry.getKey() + "--" + entry.getValue());
	}

	System.out.println(System.getProperties().getProperty("Zhou"));
	System.out.println(System.getProperty("Zhou"));

	List<String> list = Arrays.asList(split);

	// lambda表达式以及 函数操作
	Consumer<Object> tb = (Object sss) -> {
	    System.out.println("" + sss);
	};

	Predicate<String> p = (String sss) -> {
	    // System.out.println("Hello World!" + sss);
	    sss = sss.replaceAll("huggies", "KMKM");
	    System.out.println(sss);
	    return sss.length() > 4;
	};

	list.stream().filter(p).forEach(tb);

	// forEach() 方法内实际为一个Consumer 对象 其类型是forEach中 T的上界 list.forEach(tb);
	list.forEach(table -> System.out.println(table)); // 冒号操作
	list.forEach(System.out::println);

	ArrayList<Ball> ballList = new ArrayList<Ball>();
	ballList.add(new Ball("blue"));
	ballList.add(new Ball("blue"));
	ballList.add(new Ball("blue"));
	ballList.add(new Ball("blue"));
	ballList.add(new Ball("blue"));
	ballList.add(new Ball("red"));
	ballList.add(new Ball("red"));
	ballList.add(new Ball("red"));
	ballList.add(new Ball("red"));
	ballList.add(new Ball("red"));

	ballList.forEach(ball -> System.out.println(ball.toString()));

	// 流操作
	ballList.parallelStream().filter(ball -> "blue".equalsIgnoreCase(ball.getColor()))
		.forEach(ball -> ball.setColor("yellow"));

	ballList.forEach(ball -> System.out.println(ball.toString()));

	boolean b = list.stream().allMatch(stb -> stb.contains("huggies"));
	System.out.println(b);
	ArrayList<String> bList = new ArrayList<>();
	bList.addAll(list);
	boolean bb = bList.stream().allMatch(stb -> stb.contains("huggies"));
	System.out.println(bb);
	long count = bList.stream().count();
	System.out.println(count);

	Map<Object, List<String>> map = bList.stream()
		.collect(Collectors.groupingBy(x -> x.contains("huggies") ? "HCC" : "OTHER"));
	map.forEach((x, y) -> System.out.println(x + "-" + y));

	Map<Object, List<String>> map2 = bList.stream()
		.collect(Collectors.groupingBy((as) -> as.length(), Collectors.toList()));
	map2.forEach((x, y) -> System.out.println(x + "-" + y));
	Random seed = new Random(100);
	Supplier<Integer> sup = seed::nextInt;

	Stream.generate(sup).limit(5).forEach(tb);

	Stream.iterate(1, q -> q + 5).limit(5).forEach(tb);

	int max = Stream.of(3, 6, 29, 10, 33, 22, 4, 64).mapToInt(Integer::valueOf).max().getAsInt();
	int min = Stream.of(3, 6, 29, 10, 33, 22, 4, 64).mapToInt(Integer::valueOf).min().getAsInt();
	System.out.println("max=" + max + ",min=" + min);
	Stream.of(3, 6, 29, 10, 33, 22, 4, 64).forEachOrdered(tb);
	Integer sum1 = Stream.of(3, 6, 29, 10, 33, 22, 4, 64).reduce(0, (x, y) -> x + y);
	System.out.println(sum1);
	Integer sum2 = Stream.of(3, 6, 29, 10, 33, 22, 4, 64).sorted().reduce(Integer::sum).get();
	System.out.println(sum2);
	Runnable dr = System.out::println;
	System.out.println(dr);

    }
}

class Ball {
    private String color;

    public Ball(String color) {
	this.color = color;
    }

    public String getColor() {
	return color;
    }

    public void setColor(String color) {
	this.color = color;
    }

    @Override
    public String toString() {
	return "Ball [color=" + color + "]";
    }
}