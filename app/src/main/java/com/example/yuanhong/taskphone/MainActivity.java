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


//    private EditText et1;
//    private EditText et2;
//    private EditText et3;
//    private EditText et4;
////    private PhoneInfoUtils phoneInfoUtils;
//
//    static String       ISDOUBLE;
//    static String       SIMCARD;
//    static String       SIMCARD_1;
//    static String       SIMCARD_2;
//    static boolean      isDouble;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        et1 = (EditText)findViewById(R.id.editText3);
//        et2 = (EditText)findViewById(R.id.editText4);
//        et3 = (EditText)findViewById(R.id.editText5);
//        et4 = (EditText)findViewById(R.id.editText6);
//
////        getNumber();
////        getNum();
//    }



//    public void getNum() {
//        TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//
//        et1.setText(tm.getDeviceId());
//
//        et2.setText(tm.getLine1Number());//手机号码
//
//        et3.setText(tm.getSimSerialNumber());
//
//        et4.setText(tm.getSubscriberId());
//
//
//    }

//    public void init() {
//        phoneInfoUtils = new PhoneInfoUtils(this);
//        et1.setText(phoneInfoUtils.getIccid());
//        et2.setText(phoneInfoUtils.getPhoneInfo());
//        et3.setText(phoneInfoUtils.getNativePhoneNumber());
//        et4.setText(phoneInfoUtils.getProvidersName());
//    }

//    private void getNumber()
//    {
//        TelephonyManager tm = (TelephonyManager) this.getSystemService(this.TELEPHONY_SERVICE);
//        String phoneNumber1 = tm.getLine1Number();
//
//        // String phoneNumber2 = tm.getGroupIdLevel1();
//
//        initIsDoubleTelephone(this);
//        if (isDouble)
//        {
//            // tv.setText("这是双卡手机！");
//            et1.setText("本机号码是：" + "   " + phoneNumber1 + "   " + "这是双卡手机！");
//        } else
//        {
//            // tv.setText("这是单卡手机");
//            et1.setText("本机号码是：" + "   " + phoneNumber1 + "   " + "这是单卡手机");
//        }
//
//    }
//
//    public void initIsDoubleTelephone(Context context)
//    {
//        isDouble = true;
//        Method method = null;
//        Object result_0 = null;
//        Object result_1 = null;
//        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        try
//        {
//            // 只要在反射getSimStateGemini 这个函数时报了错就是单卡手机（这是我自己的经验，不一定全正确）
//            method = TelephonyManager.class.getMethod("getSimStateGemini", new Class[]
//                    { int.class });
//            // 获取SIM卡1
//            result_0 = method.invoke(tm, new Object[]
//                    { new Integer(0) });
//            // 获取SIM卡2
//            result_1 = method.invoke(tm, new Object[]
//                    { new Integer(1) });
//        } catch (SecurityException e)
//        {
//            isDouble = false;
//            e.printStackTrace();
//            // System.out.println("1_ISSINGLETELEPHONE:"+e.toString());
//        } catch (NoSuchMethodException e)
//        {
//            isDouble = false;
//            e.printStackTrace();
//            // System.out.println("2_ISSINGLETELEPHONE:"+e.toString());
//        } catch (IllegalArgumentException e)
//        {
//            isDouble = false;
//            e.printStackTrace();
//        } catch (IllegalAccessException e)
//        {
//            isDouble = false;
//            e.printStackTrace();
//        } catch (InvocationTargetException e)
//        {
//            isDouble = false;
//            e.printStackTrace();
//        } catch (Exception e)
//        {
//            isDouble = false;
//            e.printStackTrace();
//            // System.out.println("3_ISSINGLETELEPHONE:"+e.toString());
//        }
//        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = sp.edit();
//        if (isDouble)
//        {
//            // 保存为双卡手机
//            editor.putBoolean(ISDOUBLE, true);
//            // 保存双卡是否可用
//            // 如下判断哪个卡可用.双卡都可以用
//            if (result_0.toString().equals("5") && result_1.toString().equals("5"))
//            {
//                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
//                {
//                    editor.putString(SIMCARD, "0");
//                }
//                editor.putBoolean(SIMCARD_1, true);
//                editor.putBoolean(SIMCARD_2, true);
//
//                et2.setText("双卡可用");
//
//            } else if (!result_0.toString().equals("5") && result_1.toString().equals("5"))
//            {// 卡二可用
//                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
//                {
//                    editor.putString(SIMCARD, "1");
//                }
//                editor.putBoolean(SIMCARD_1, false);
//                editor.putBoolean(SIMCARD_2, true);
//
//                et2.setText("卡二可用");
//
//            } else if (result_0.toString().equals("5") && !result_1.toString().equals("5"))
//            {// 卡一可用
//                if (!sp.getString(SIMCARD, "2").equals("0") && !sp.getString(SIMCARD, "2").equals("1"))
//                {
//                    editor.putString(SIMCARD, "0");
//                }
//                editor.putBoolean(SIMCARD_1, true);
//                editor.putBoolean(SIMCARD_2, false);
//
//                et2.setText("卡一可用");
//
//            } else
//            {// 两个卡都不可用(飞行模式会出现这种种情况)
//                editor.putBoolean(SIMCARD_1, false);
//                editor.putBoolean(SIMCARD_2, false);
//
//                et2.setText("飞行模式");
//            }
//        } else
//        {
//            // 保存为单卡手机
//            editor.putString(SIMCARD, "0");
//            editor.putBoolean(ISDOUBLE, false);
//        }
//        editor.commit();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


}
