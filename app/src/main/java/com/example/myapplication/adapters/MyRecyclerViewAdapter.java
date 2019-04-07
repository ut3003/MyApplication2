package com.example.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.models.MyModel;
import com.example.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<MyModel> myModelList;
    Context mContext;

    public MyRecyclerViewAdapter(List<MyModel> myModelList, Context context) {
        mContext = context;
        this.myModelList = myModelList;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.list_item_layout, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
        MyModel myList = myModelList.get(position);
        holder.mName.setText(myList.getTitle());

        //Set Image with gilde

        RequestOptions defaultOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background); //by default we will set this image
        Glide.with(mContext)
                .setDefaultRequestOptions(defaultOptions)
                .load(myList.getImageUrl()) // loading actual image
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return myModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mImage;
        private TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image);
            mName = itemView.findViewById(R.id.image_name);
        }
    }
}
