package com.example.yuanhong.taskphone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.CallLog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private MyAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("go onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelist);
        lv = (ListView) findViewById(R.id.callView);

        ContactsMsgUtils contactsMsgUtils = new ContactsMsgUtils();
        List<CallLogInfo> infos = contactsMsgUtils.getCallLog(this);
        adapter = new MyAdapter(infos);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CallLogInfo info = (CallLogInfo) adapter.getItem(position);
                final String number = info.number;
                String[] items = new String[] { "复制号码到拨号盘, 拨号, 发送短信 "};

                new AlertDialog.Builder(MainActivity.this).setTitle("操作")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        startActivity(new Intent(
                                                Intent.ACTION_DIAL, Uri
                                                .parse("tel:" + number)));
                                        break;
                                    case 1:
                                        startActivity(new Intent(
                                                Intent.ACTION_CALL, Uri
                                                .parse("tel:" + number)));
                                        break;
                                    case 2:
                                        startActivity(new Intent(
                                                Intent.ACTION_SENDTO, Uri
                                                .parse("sms:" + number)));
                                        break;

                                    default:
                                        break;
                                }
                            }
                        }).show();

                return false;
            }
        });

    }


    private class MyAdapter extends BaseAdapter {
        private List<CallLogInfo> infos;
        private LayoutInflater inflater;

        public MyAdapter(List<CallLogInfo> infos) {
            super();
            this.infos = infos;
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {

            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = inflater.inflate(R.layout.call_log_item, null);

            TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
            TextView tv_date = (TextView) view.findViewById(R.id.tv_date);
            TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
            CallLogInfo info = infos.get(position);
            tv_number.setText(info.number);
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd hh:mm:ss");
            String dateStr = format.format(info.date);
            tv_date.setText(dateStr);
            String typeStr = null;
            int color = 0;
            switch (info.type) {
                case CallLog.Calls.INCOMING_TYPE:
                    typeStr = "来电";
                    color = Color.BLUE;

                    break;
                case CallLog.Calls.OUTGOING_TYPE:
                    typeStr = "去电";
                    color = Color.GREEN;

                    break;
                case CallLog.Calls.MISSED_TYPE:
                    typeStr = "未接";
                    color = Color.RED;

                    break;

                default:
                    break;
            }
            tv_type.setText(typeStr);
            tv_type.setTextColor(color);
            return view;
        }

    }





}
