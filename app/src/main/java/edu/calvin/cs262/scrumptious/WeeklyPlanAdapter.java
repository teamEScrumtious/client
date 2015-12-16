/**
 * WeeklyPlanAdapter.java
 *
 * Created by tjluce on 11/11/15.
 *
 * Adapts the dishes in the weekly plan into a expandable list
 *
 * Uses code based on http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */

package edu.calvin.cs262.scrumptious;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class WeeklyPlanAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Dish>> _listDataChild;

    public WeeklyPlanAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<Dish>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //final String childText = (String) getChild(groupPosition, childPosition);
        Dish dish = (Dish) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.weekly_plan_expand_child, null);
        }

        TextView tvRecipeName = (TextView) convertView
                .findViewById(R.id.tvRecipeName);
        TextView tvRecipeServings = (TextView) convertView
                .findViewById(R.id.tvRecipeServings);

        tvRecipeName.setText(dish.getRecipe().getName());
        tvRecipeServings.setText(String.valueOf("Servings: " + dish.getServings()));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.weekly_plan_expand_header, null);
        }

        TextView lblDayOfWeek = (TextView) convertView
                .findViewById(R.id.dayOfWeek);
        lblDayOfWeek.setTypeface(null, Typeface.BOLD);
        lblDayOfWeek.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
