package sam.alarmclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class OptionAdapter extends ArrayAdapter<String>{
    public OptionAdapter(Context context, String[] foods) {
        super(context, R.layout.option_row ,foods);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // default -  return super.getView(position, convertView, parent);
        // add the layout
        LayoutInflater myCustomInflater = LayoutInflater.from(getContext());
        View customView = myCustomInflater.inflate(R.layout.option_row, parent, false);
        // get references.
        String singleFoodItem = getItem(position);
        TextView itemText = (TextView) customView.findViewById(R.id.item_text);

        // dynamically update the text from the array
        itemText.setText(singleFoodItem);

        return customView;
    }
}
