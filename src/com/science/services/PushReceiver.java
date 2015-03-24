package com.science.services;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.science.activity.CommonContentActivity;
import com.science.util.AppUtil;

public class PushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
		switch (bundle.getInt(PushConsts.CMD_ACTION)) {

		case PushConsts.GET_MSG_DATA:
			// 获取透传数据
			// String appid = bundle.getString("appid");
			byte[] payload = bundle.getByteArray("payload");
			
			String taskid = bundle.getString("taskid");
			String messageid = bundle.getString("messageid");

			// smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
			boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
			System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));
			
			if (payload != null) {

				try {
					String data = new String(payload);
					int a=data.indexOf("{");
					data=data.substring(a);
			        JSONObject obj;
					obj = new JSONObject(data);
					String url=obj.getString("url");
					String id=obj.getString("block_id");
					String name=AppUtil.BlockCodeToBlockText(id);
					
					
					Intent intentContent=new Intent(context,CommonContentActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra("url", url);//传递数据
					intent.putExtra("title", name);
					intent.putExtra("act_class", name);
					//intent.setAction("com.science.CommonContentActivity");
					context.startActivity(intent);
					
					Log.d("GetuiSdkDemo", "Got Payload:" + data);
//					if (GetuiSdkDemoActivity.tLogView != null)
//						GetuiSdkDemoActivity.tLogView.append(data + "\n");
					Toast.makeText(context, "透传测试"+data, Toast.LENGTH_LONG).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        

				
			}
			break;
		case PushConsts.GET_CLIENTID:
			// 获取ClientID(CID)
			// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
			String cid = bundle.getString("clientid");
//			if (GetuiSdkDemoActivity.tView != null)
//				GetuiSdkDemoActivity.tView.setText(cid);
			break;
		case PushConsts.THIRDPART_FEEDBACK:
			/*String appid = bundle.getString("appid");
			String taskid = bundle.getString("taskid");
			String actionid = bundle.getString("actionid");
			String result = bundle.getString("result");
			long timestamp = bundle.getLong("timestamp");

			Log.d("GetuiSdkDemo", "appid = " + appid);
			Log.d("GetuiSdkDemo", "taskid = " + taskid);
			Log.d("GetuiSdkDemo", "actionid = " + actionid);
			Log.d("GetuiSdkDemo", "result = " + result);
			Log.d("GetuiSdkDemo", "timestamp = " + timestamp);*/
			break;
		default:
			break;
		}
	}
}
