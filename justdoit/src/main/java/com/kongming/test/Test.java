package com.kongming.test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

public class Test {
	public static void main(String[] args) {
		String str = "T3_2012_GameResultUser, pants, ProductTrial_UserRequest, T3campaign, T4_picasso_Users, vertical_user, campaign20140318, huggies_t5_hongbao_duijiang, huggies_fetalmove_records, T5trail, huggies_double_eleven, huggies_T3_coupon, EC, Jean_Photos, huggies_pome_coupon, happy40weeks, app, t5hotmom, huggies_invite_code, T3weibo, huggies_invite_coupon_xinsheng, huggies_invite_code_xinsheng, T5apply, message, Hospital_upload, T3_Trial_User, huggies_t3_mingdan, Pants_EDM_Confirm, huggies_invite_code_T3, weibowin, weixin, weixin_user, Pome_UserUpload, PantsLaunch_Log_PlayGame, EC2014, baby_info, huggies_T3_address, Hospital_0128, huggies_t3_prize, huggies_invite_coupon, Kongfu_Photos, customer, T4_UserData";
		String str1 = "weixin,weixin_user,huggies_double_eleven,huggies_fetalmove_records,huggies_invite_code,huggies_invite_code_T3,huggies_invite_code_xinsheng,huggies_invite_coupon,huggies_invite_coupon_xinsheng,huggies_pome_coupon,huggies_T3_address,huggies_T3_coupon,huggies_t3_mingdan,huggies_t3_prize,huggies_t5_hongbao_duijiang,baby_info,vertical_user,campaign20140318,T5trail,t5hotmom,T5apply,T3weibo,T3campaign,pants,message,app,weibowin,customer,T4_UserData,T4_picasso_Users,T3_Trial_User,T3_2012_GameResultUser,ProductTrial_UserRequest,Pome_UserUpload,PantsLaunch_Log_PlayGame,Pants_EDM_Confirm,Kongfu_Photos,Jean_Photos,Hospital_upload,Hospital_0128,happy40weeks,EC,EC2014";
		String[] split = str.split(", ");
		String[] split1 = str1.split(",");
		HashSet<String> set = new HashSet<>(Arrays.asList(split));
		HashSet<String> set1 = new HashSet<>(Arrays.asList(split1));

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
			Entry<java.lang.Object, java.lang.Object> entry = iterator
					.next();
			
			System.out.println(entry.getKey()+"--"+entry.getValue());
		}
		
		System.out.println(System.getProperties().getProperty("Zhou"));
		System.out.println(System.getProperty("Zhou"));
	}
}