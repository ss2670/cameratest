package com.example.demoapk;




import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.widget.Toast;

/**
 * 
 * @ClassName: AlarmReceiver  
 * @Description: ����ʱ�䵽�˻��������㲥�����ʱ�������һЩ������ҵ��
 * @author HuHood
 * @date 2013-11-25 ����4:44:30  
 *
 */
public class AlarmReceiver extends BroadcastReceiver {
	
	@Override
    public void onReceive(Context context, Intent intent) {
	Toast.makeText(context, "��������, ��������������~~", Toast.LENGTH_LONG).show();
    }

}
