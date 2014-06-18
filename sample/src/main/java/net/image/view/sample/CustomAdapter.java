package net.image.view.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import net.image.view.NetImageView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    private final LayoutInflater mInflater;

    public CustomAdapter(Context context, List<String> list) {
        super(context, 0, list);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String url = getItem(position);
        holder.imageView.setImageUrl(url);
        return convertView;
    }

    static class ViewHolder {
        NetImageView imageView;
        public ViewHolder(View view) {
            imageView = (NetImageView) view.findViewById(R.id.image);
        }
    }

}
