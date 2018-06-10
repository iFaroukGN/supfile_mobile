package tg.geek228.supfile.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tg.geek228.supfile.Interface.OnItemClickListener;
import tg.geek228.supfile.R;

public class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView resourceName;
    private ImageView resourceImg;

    private OnItemClickListener mItemClickListener;

    public ViewHolder(View itemView) {
        super(itemView);

        resourceImg = (ImageView) itemView.findViewById(R.id.resourceImg);
        resourceName = (TextView) itemView.findViewById(R.id.resourceName);

        itemView.setOnClickListener(this);
    }


    public TextView getResourceName() {
        return resourceName;
    }

    public void setResourceName(TextView resourceName) {
        this.resourceName = resourceName;
    }

    public ImageView getResourceImg() {
        return resourceImg;
    }

    public void setResourceImg(ImageView resourceImg) {
        this.resourceImg = resourceImg;
    }

    @Override
    public void onClick(View view) {
        if(view != null && mItemClickListener != null ) {
            mItemClickListener.onCLick(view, getPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

