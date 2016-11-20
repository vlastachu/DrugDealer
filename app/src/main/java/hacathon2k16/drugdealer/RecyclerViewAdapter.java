package hacathon2k16.drugdealer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vlastachu on 19/11/16.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<String> tipsValues;
    Context context;
    View view;
    ViewHolder viewHolder;
    TextView textView;

    public RecyclerViewAdapter(Context context, List<String> tipsValues){

        this.tipsValues = tipsValues;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textView;

        public ViewHolder(View v){

            super(v);

            textView = (TextView)v.findViewById(R.id.tip_textview);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view = LayoutInflater.from(context).inflate(R.layout.tip_item, parent, false);

        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        holder.textView.setText(tipsValues.get(position));
    }

    @Override
    public int getItemCount(){

        return tipsValues.size();
    }
}
