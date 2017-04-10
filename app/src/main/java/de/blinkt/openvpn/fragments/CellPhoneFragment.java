package de.blinkt.openvpn.fragments;

/**
 * Created by Administrator on 2016/9/8 0008.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;

import cn.com.aixiaoqi.R;
import cn.com.johnson.adapter.CellPhoneFragmentPagerAdapter;
import cn.com.johnson.model.AppMode;
import de.blinkt.openvpn.activities.ProMainActivity;
import de.blinkt.openvpn.activities.SMSAcivity;
import de.blinkt.openvpn.util.ViewUtil;

import static de.blinkt.openvpn.constant.UmengContant.CLICKEDITSMS;
import static de.blinkt.openvpn.constant.UmengContant.CLICKTITLEPHONE;
import static de.blinkt.openvpn.constant.UmengContant.CLICKTITLESMS;


public class CellPhoneFragment extends Fragment {
    public static RadioGroup operation_rg;
    RadioButton cell_phone_rb;
    RadioButton message_rb;
    // public static EditTextWithDel dial_input_edit_text;
    /**
     * 输入框的外部布局
     */
    // public static LinearLayout dial_input_edit_ll;
    /**
     * 拨打电话标题
     */
    public static FrameLayout dial_tittle_fl;

    // ImageView dial_delete_btn_iv;

    TextView editTv;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    Activity activity;
    ViewPager mViewPager;
    //悬浮按钮
    public static ImageView floatingActionButton;
    public static boolean isForeground = false;
    Drawable drawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.cell_phone_fragment, container, false);
        initView(view);
        addListener();
        return view;
    }


    @Override

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isForeground = isVisibleToUser;
        if (isVisibleToUser) {

            operation_rg.check(cell_phone_rb.getId());
            ClickPhone();
        /* if (CellPhoneFragment.dial_input_edit_text.getVisibility() == View.VISIBLE) {
                showPhoneBottomBar();
            } else {
                hidePhoneBottomBar();
            }*/
           // ProMainActivity.phone_linearLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroy() {
        operation_rg = null;
        // dial_input_edit_text = null;
        phoneFragment = null;
        dial_tittle_fl = null;
        //dial_input_edit_ll=null;
        floatingActionButton = null;
        super.onDestroy();
    }

    public void hidePhoneBottomBar() {
        ProMainActivity.bottom_bar_linearLayout.setVisibility(View.VISIBLE);
        ProMainActivity.phone_linearLayout.setVisibility(View.GONE);
    }

    public void showPhoneBottomBar() {
        ProMainActivity.bottom_bar_linearLayout.setVisibility(View.GONE);
        ProMainActivity.phone_linearLayout.setVisibility(View.VISIBLE);
    }

    private void addListener() {
     /* dial_input_edit_text
                .setCursorVisible(false);*/
    /*   dial_input_edit_text.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });*/
        editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //友盟方法统计
                MobclickAgent.onEvent(getActivity(), CLICKEDITSMS);
                Intent intent = new Intent(getActivity(), SMSAcivity.class);
                startActivity(intent);
            }
        });

        /**
         * 悬浮按钮事件
         */
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatingActionButton.setVisibility(View.GONE);
                //展示电话键
                ViewUtil.showView(phoneFragment.t9dialpadview);
                hidePhoneBottomBar();
                if (ProMainActivity.phone_fl.getVisibility() == View.GONE || ProMainActivity.phone_fl.getVisibility() == View.INVISIBLE)

                    ProMainActivity.phone_fl.setVisibility(View.VISIBLE);


                if (null != AppMode.getInstance().curCharacter) {
                    if (AppMode.getInstance().curCharacter.length() > 0) {
                        showPhoneBottomBar();
                    }


                }
            }
        });


    }

    private void initView(View view) {


        operation_rg = ((RadioGroup) view.findViewById(R.id.operation_rg));
        cell_phone_rb = ((RadioButton) view.findViewById(R.id.cell_phone_rb));
        //  dial_input_edit_text = ((EditTextWithDel) view.findViewById(R.id.dial_input_edit_text));


        //拨打电话标题
        dial_tittle_fl = (FrameLayout) view.findViewById(R.id.dial_tittle_fl);


        message_rb = ((RadioButton) view.findViewById(R.id.message_rb));
        editTv = ((TextView) view.findViewById(R.id.edit_tv));
        mViewPager = (ViewPager) view.findViewById(R.id.mViewPager);

        //悬浮按钮
        floatingActionButton = (ImageView) view.findViewById(R.id.floatingActionButton);
        initFragment();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    ClickPhone();
                } else {
                    ClickMessage();

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //初始化标题下标的小三角

        drawable = getActivity().getResources().getDrawable(R.drawable.image_slidethetriangle);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        this.activity = activity;
        super.onAttach(activity);
    }

    public void setFragment_Phone(Fragment_Phone phoneFragment) {
        CellPhoneFragment.phoneFragment = phoneFragment;
    }

    static Fragment_Phone phoneFragment;

    private void initFragment() {
        fragments.clear();//清空
        fragments.add(phoneFragment);
        fragments.add(new SmsFragment());
        CellPhoneFragmentPagerAdapter mAdapter = new CellPhoneFragmentPagerAdapter(getChildFragmentManager(), fragments);
        mAdapter.setFragments(fragments);
        mViewPager.setAdapter(mAdapter);
        operation_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cell_phone_rb:
                        //友盟方法统计
                        MobclickAgent.onEvent(getActivity(), CLICKTITLEPHONE);
                        ClickPhone();
                        break;
                    case R.id.message_rb:
                        //友盟方法统计
                        MobclickAgent.onEvent(getActivity(), CLICKTITLESMS);
                        ClickMessage();
                        break;
                }
            }
        });
    }

    private void ClickMessage() {
        // cell_phone_rb.setBackgroundResource(R.drawable.default_top_cell);
        // message_rb.setBackgroundResource(R.drawable.select_top_cell_sms);
        //  cell_phone_rb.setTextColor(Color.BLACK);
        //  message_rb.setTextColor(Color.WHITE);

        message_rb.setCompoundDrawables(null, null, null, drawable);
        cell_phone_rb.setCompoundDrawables(null, null, null, null);
        mViewPager.setCurrentItem(1);
        editTv.setVisibility(View.VISIBLE);

      // ViewUtil.hideView(phoneFragment.t9dialpadview);
        floatingActionButton.setVisibility(View.GONE);
    }

    private void ClickPhone() {
        //  cell_phone_rb.setBackgroundResource(R.drawable.select_top_cell);
        //   message_rb.setBackgroundResource(R.drawable.default_top_cell_sms);
        //   cell_phone_rb.setTextColor(Color.WHITE);
        //  message_rb.setTextColor(Color.BLACK);

       //ViewUtil.hideView(phoneFragment.t9dialpadview);
        floatingActionButton.setVisibility(View.VISIBLE);

        message_rb.setCompoundDrawables(null, null, null, null);
        cell_phone_rb.setCompoundDrawables(null, null, null, drawable);
        editTv.setVisibility(View.GONE);
        mViewPager.setCurrentItem(0);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
