package zhuoxin.andriody.com.mygithub.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import zhuoxin.andriody.com.mygithub.R;
import zhuoxin.andriody.com.mygithub.favorite.model.LocalRepo;

/**
 * Created by 123 on 2016/8/31.
 */
public class FavoriteAdapter extends BaseAdapter{

    private List<LocalRepo> datas;

    public FavoriteAdapter() {
        datas = new ArrayList<>();
    }

    public void setData(List<LocalRepo> localRepos){
        datas.clear();
        datas.addAll(localRepos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas==null?0:datas.size();
    }

    @Override
    public LocalRepo getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_repo,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        LocalRepo localRepo = getItem(position);
        viewHolder.tvRepoName.setText(localRepo.getFullName());
        viewHolder.tvRepoInfo.setText(localRepo.getDescription());
        viewHolder.tvRepoStars.setText("stars："+localRepo.getStargazersCount());
        ImageLoader.getInstance().displayImage(localRepo.getAvatarUrl(),viewHolder.ivIcon);
        return convertView;
    }
    static class ViewHolder{
        @Bind(R.id.ivIcon)
        ImageView ivIcon;
        @Bind(R.id.tvRepoName)
        TextView tvRepoName;
        @Bind(R.id.tvRepoInfo) TextView tvRepoInfo;
        @Bind(R.id.tvRepoStars) TextView tvRepoStars;

        ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
