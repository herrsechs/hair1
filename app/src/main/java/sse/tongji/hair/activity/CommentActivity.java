package sse.tongji.hair.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.socialization.Comment;
import cn.sharesdk.socialization.CommentFilter;
import cn.sharesdk.socialization.CommentListPage;
import cn.sharesdk.socialization.QuickCommentBar;
import cn.sharesdk.socialization.Socialization;
import cn.sharesdk.socialization.SocializationCustomPlatform;
import cn.sharesdk.socialization.component.CommentListView;
import sse.tongji.hair.R;


public class CommentActivity extends AppCompatActivity{

    private QuickCommentBar qcBar;
    private OnekeyShare oks;
    private String topicId = "aaa";
    // 模拟的主题标题
    private String topicTitle = "bbb";
    // 模拟的主题发布时间
    private String topicPublishTime = "ccc";
    // 模拟的主题作者
    private String topicAuthor = "ddd";
    private CommentFilter filter;
    private Context context;
    private Button button;
    private CommentListView commentListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = CommentActivity.this;
        ShareSDK.initSDK(context);
        ShareSDK.registerService(Socialization.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fake_main);
        initQuickCommentBar();


//        Log.d("Service_Version_Name", socialization.getServiceVersionName());
//        ArrayList<Comment> arrayComment = new ArrayList<>();
//        arrayComment = socialization.getCommentList("aaa");
//        Log.d("Comment number", String.valueOf(arrayComment.size()));


    }

    private void initQuickCommentBar() {
        qcBar = (QuickCommentBar) findViewById(R.id.qcBar);
        qcBar.setTopic(topicId, topicTitle, topicPublishTime, topicAuthor);
        qcBar.setTextToShare("share_content");

        qcBar.setAuthedAccountChangeable(false);
        qcBar.getBackButton().setVisibility(View.GONE);
        qcBar.getChildAt(3).setVisibility(View.GONE);

        CommentFilter.Builder builder = new CommentFilter.Builder();
        // 非空过滤器 -- “评论内容不合法，请重新输入”
        builder.append(new CommentFilter.FilterItem() {
            // 返回true表示是垃圾评论
            public boolean onFilter(String comment) {
                if (TextUtils.isEmpty(comment)) {
                    return true;
                } else if (comment.trim().length() <= 0) {
                    return true;
                } else if (comment.trim().toLowerCase().equals("null")) {
                    return true;
                }
                return false;
            }

            @Override
            public int getFilterCode() {
                return 0;
            }
        });
        // 字数上限过滤器 -- “评论内容不合法，请重新输入”
        builder.append(new CommentFilter.FilterItem() {
            // 返回true表示是超长评论
            public boolean onFilter(String comment) {
                if (comment != null) {
                    String pureComment = comment.trim();
                    String wordText = com.mob.tools.utils.R.toWordText(pureComment, 140);
                    if (wordText.length() != pureComment.length()) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public int getFilterCode() {
                return 0;
            }
        });
        filter = builder.build();
        qcBar.setCommentFilter(filter);
        commentListView = (CommentListView) findViewById(R.id.CommentListview);
        this.commentListView.a(true);
        this.commentListView.a(topicId, topicTitle, topicPublishTime, topicAuthor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_src_text) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
