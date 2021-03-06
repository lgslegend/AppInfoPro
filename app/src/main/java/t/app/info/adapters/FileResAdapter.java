package t.app.info.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.utils.app.info.AppInfoBean;
import dev.utils.app.toast.ToastUtils;
import dev.utils.common.FileUtils;
import t.app.info.R;
import t.app.info.activitys.ApkDetailsActivity;
import t.app.info.beans.item.FileResItem;
import t.app.info.utils.config.KeyConstants;
import t.app.info.utils.config.NotifyConstants;

/**
 * detail:文件资源 Adapter
 * Created by Ttt
 */
public class FileResAdapter extends RecyclerView.Adapter<FileResAdapter.ViewHolder>{

    // 上下文
    private Activity mActivity;
    // 数据源
    private ArrayList<FileResItem> listDatas = new ArrayList<>();

    /**
     * 初始化适配器
     * @param mActivity
     */
    public FileResAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        // 判断类型
        if (viewType == 0){
            View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_app_info, null, false);
            viewHolder = new ViewHolder(itemView, viewType);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 获取实体类
        FileResItem fileResItem = listDatas.get(position);
        // 判断类型
        if (holder.iType == 0){
            // 获取app 信息
            AppInfoBean appInfoBean = fileResItem.getAppInfoBean();
            // 设置 app 名
            holder.iai_name_tv.setText(appInfoBean.getAppName() + "");
            // 设置 app 包名
            holder.iai_pack_tv.setText(appInfoBean.getAppPackName() + "");
            // 设置 app 图标
            holder.iai_igview.setImageDrawable(appInfoBean.getAppIcon());
        }
    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    // ==

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        int iType;
        // == View ==
        RelativeLayout iai_rela;
        ImageView iai_igview;
        TextView iai_name_tv;
        TextView iai_pack_tv;

        public ViewHolder(View itemView, int iType){
            super(itemView);
            this.iType = iType;
            // 判断类型
            if (iType == 0) {
                iai_rela = (RelativeLayout) itemView.findViewById(R.id.iai_rela);
                iai_igview = (ImageView) itemView.findViewById(R.id.iai_igview);
                iai_name_tv = (TextView) itemView.findViewById(R.id.iai_name_tv);
                iai_pack_tv = (TextView) itemView.findViewById(R.id.iai_pack_tv);
                // 设置点击事件
                iai_rela.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            // 判断类型
            if (iType == 0){
                // 获取当前索引
                int pos = getLayoutPosition();
                // 获取Item
                FileResItem fileResItem = listDatas.get(pos);
                // 文件存在处理
                if (FileUtils.isFileExists(fileResItem.getUri())){
                    // 进行跳转
                    Intent intent = new Intent(mActivity, ApkDetailsActivity.class);
                    intent.putExtra(KeyConstants.KEY_APK_URI, fileResItem.getUri());
                    mActivity.startActivityForResult(intent, NotifyConstants.FOR_R_APP_DETAILS);
                } else {
                    ToastUtils.showShort(mActivity, R.string.file_not_exist);
                }
            }
        }
    }

    // ==

    /**
     * 设置数据源
     * @param listDatas
     */
    public void setListDatas(ArrayList<FileResItem> listDatas) {
        if (listDatas == null){
            return;
        }
        this.listDatas = listDatas;
        // 刷新适配器
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearData(){
        this.listDatas.clear();
        // 刷新适配器
        notifyDataSetChanged();
    }
}
