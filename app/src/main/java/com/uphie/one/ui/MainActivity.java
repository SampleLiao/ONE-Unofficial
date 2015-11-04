package com.uphie.one.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.umeng.analytics.MobclickAgent;
import com.uphie.one.R;
import com.uphie.one.common.App;
import com.uphie.one.interfaces.ShareChannel;
import com.uphie.one.ui.article.ArticleFragment;
import com.uphie.one.ui.home.HomeFragment;
import com.uphie.one.ui.personal.PersonalFragment;
import com.uphie.one.ui.question.QuestionFragment;
import com.uphie.one.ui.thing.ThingFragment;
import com.uphie.one.utils.SysUtil;
import com.uphie.one.utils.TextToast;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Uphie on 2015/9/5.
 * Email: uphie7@gmail.com
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, View.OnTouchListener {

    @Bind(R.id.tab_group)
    RadioGroup tabGroup;
    @Bind(R.id.action_bar_more)
    TextView actionBarMore;
    @Bind(R.id.main_content)
    FrameLayout mainContent;

    private HomeFragment homeFragment;
    private ArticleFragment articleFragment;
    private QuestionFragment questionFragment;
    private ThingFragment thingFragment;
    private PersonalFragment personalFragment;

    private FragmentManager fragmentManager;

    private PopupWindow sharePanel;
    private long curTime;
    private long firClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        App.displayMetrics = SysUtil.getDisplayMetrics(this);

        actionBarMore.setOnClickListener(this);
        tabGroup.setOnCheckedChangeListener(this);
        mainContent.setLongClickable(true);
        mainContent.setOnTouchListener(this);

        fragmentManager = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_content, homeFragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainPage");
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainPage");
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //2秒之内的连续按返回键视为退出，防止误操作
            if (System.currentTimeMillis() - curTime < 2000) {
                finish();
                MobclickAgent.onKillProcess(this);
                System.exit(0);
            } else {
                TextToast.shortShow("再按一次退出");
                curTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.action_bar_more:
                if (tabGroup.getCheckedRadioButtonId() != R.id.tab_personal) {
                    showSharePanel();
                }
                break;
            case R.id.share_facebook:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.FACEBOOK, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.FACEBOOK, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.FACEBOOK, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.FACEBOOK, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_twitter:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.TWITTER, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.TWITTER, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.TWITTER, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.TWITTER, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_google_plus:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.GOOGLE_PLUS, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.GOOGLE_PLUS, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.GOOGLE_PLUS, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.GOOGLE_PLUS, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_wechat:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.WECHAT, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.WECHAT, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.WECHAT, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.WECHAT, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_weibo:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.WEIBO, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.WEIBO, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.WEIBO, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.WEIBO, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_qq:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.QQ, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.QQ, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.QQ, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.QQ, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
                break;
            case R.id.share_qzone:
                switch (tabGroup.getCheckedRadioButtonId()) {
                    case R.id.tab_home:
                        homeFragment.share(ShareChannel.QZONE, homeFragment.getCurSaying());
                        break;
                    case R.id.tab_article:
                        articleFragment.share(ShareChannel.QZONE, articleFragment.getCurArticle());
                        break;
                    case R.id.tab_question:
                        questionFragment.share(ShareChannel.QZONE, questionFragment.getCurQuestion());
                        break;
                    case R.id.tab_thing:
                        thingFragment.share(ShareChannel.QZONE, thingFragment.getCurThing());
                        break;
                }
                sharePanel.dismiss();
            case R.id.btn_cancel:
                sharePanel.dismiss();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (articleFragment != null) {
            transaction.hide(articleFragment);
        }
        if (questionFragment != null) {
            transaction.hide(questionFragment);
        }
        if (thingFragment != null) {
            transaction.hide(thingFragment);
        }
        if (personalFragment != null) {
            transaction.hide(personalFragment);
        }
        switch (checkedId) {
            case R.id.tab_home:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.main_content, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case R.id.tab_article:
                if (articleFragment == null) {
                    articleFragment = new ArticleFragment();
                    transaction.add(R.id.main_content, articleFragment);
                } else {
                    transaction.show(articleFragment);
                }
                break;
            case R.id.tab_question:
                if (questionFragment == null) {
                    questionFragment = new QuestionFragment();
                    transaction.add(R.id.main_content, questionFragment);
                } else {
                    transaction.show(questionFragment);
                }
                break;
            case R.id.tab_thing:
                if (thingFragment == null) {
                    thingFragment = new ThingFragment();
                    transaction.add(R.id.main_content, thingFragment);
                } else {
                    transaction.show(thingFragment);
                }
                break;
            case R.id.tab_personal:
                if (personalFragment == null) {
                    personalFragment = new PersonalFragment();
                    transaction.add(R.id.main_content, personalFragment);
                } else {
                    transaction.show(personalFragment);
                }
                break;
        }
        transaction.commit();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == mainContent && event.getAction() == MotionEvent.ACTION_DOWN) {
            if (firClickTime == 0) {
                firClickTime = System.currentTimeMillis();
                Log.d("firClickTime", firClickTime + "ms");
            } else {
                if (System.currentTimeMillis() - firClickTime < 1000) {
                    Log.d("双击间隔", System.currentTimeMillis() - firClickTime + "ms");
                    //双击分享,这里还有问题
                    if (tabGroup.getCheckedRadioButtonId() != R.id.tab_personal) {
                        showSharePanel();
                    }
                }
                firClickTime = 0;
                return true;
            }
        }
        return false;
    }

    private void showSharePanel() {
        if (sharePanel == null) {
            View view = View.inflate(this, R.layout.menu_share, null);
            view.findViewById(R.id.share_facebook).setOnClickListener(this);
            view.findViewById(R.id.share_twitter).setOnClickListener(this);
            view.findViewById(R.id.share_wechat).setOnClickListener(this);
            view.findViewById(R.id.share_weibo).setOnClickListener(this);
            view.findViewById(R.id.share_qq).setOnClickListener(this);
            view.findViewById(R.id.share_qzone).setOnClickListener(this);
            view.findViewById(R.id.btn_cancel).setOnClickListener(this);

            sharePanel = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            sharePanel.setOutsideTouchable(true);
            sharePanel.setAnimationStyle(R.style.FadeStyle);
            sharePanel.setFocusable(true);
            sharePanel.setBackgroundDrawable(new BitmapDrawable());
            sharePanel.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                    layoutParams.alpha = 1f;
                    getWindow().setAttributes(layoutParams);
                }
            });
            sharePanel.showAtLocation(findViewById(R.id.main_content), Gravity.BOTTOM, 0, 0);
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
            layoutParams.alpha = 0.3f;
            getWindow().setAttributes(layoutParams);
        } else {
            if (!sharePanel.isShowing()) {
                sharePanel.showAtLocation(findViewById(R.id.main_content), Gravity.BOTTOM, 0, 0);
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 0.3f;
                getWindow().setAttributes(layoutParams);
            } else {
                sharePanel.dismiss();
            }
        }

    }
}
