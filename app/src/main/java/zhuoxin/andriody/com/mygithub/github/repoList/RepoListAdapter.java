package zhuoxin.andriody.com.mygithub.github.repoList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;
import zhuoxin.andriody.com.mygithub.R;
import zhuoxin.andriody.com.mygithub.github.repoList.model.Repo;

public class RepoListAdapter extends BaseAdapter{

    private ArrayList<Repo> datas;

    public RepoListAdapter() {
        datas = new ArrayList<>();
    }

    public void addAll(Collection<Repo> repos){
        datas.addAll(repos);
        notifyDataSetChanged();
    }

    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }

    @Override public int getCount() {
        return datas==null?0:datas.size();
    }

    @Override public Repo getItem(int position) {
        return datas.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_repo,parent,false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Repo repo = datas.get(position);
        viewHolder.tvRepoName.setText(repo.getFullName());
        viewHolder.tvRepoInfo.setText(repo.getDescription());
        viewHolder.tvRepoStars.setText(repo.getStarCount()+"");
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),viewHolder.ivIcon);
        return convertView;
    }

    static class ViewHolder{
        @Bind(R.id.ivIcon) ImageView ivIcon;
        @Bind(R.id.tvRepoName) TextView tvRepoName;
        @Bind(R.id.tvRepoInfo) TextView tvRepoInfo;
        @Bind(R.id.tvRepoStars) TextView tvRepoStars;

       ViewHolder(View view) {
           ButterKnife.bind(this,view);
        }
    }

}
