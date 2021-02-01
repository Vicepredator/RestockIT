package com.vicepredator.restockit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.vicepredator.restockit.R;
import com.vicepredator.restockit.MyAdapter2;

public class recyclerHelper extends ItemTouchHelper.SimpleCallback {

    private MyAdapter2 adapter;

    public recyclerHelper(MyAdapter2 adapter) {
        super(0, ItemTouchHelper.LEFT/* | ItemTouchHelper.RIGHT*/);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        final int pos = viewHolder.getAdapterPosition();

        if(direction == ItemTouchHelper.LEFT){

            AlertDialog.Builder builder = new AlertDialog.Builder(adapter.getContext());

            builder.setTitle("Deleting");
            builder.setMessage("Vuoi eliminare la nota?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    adapter.deleteProduct(pos);

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());

                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
        else{
            //adapter.editProduct(pos);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon;
        ColorDrawable backgroundColor;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if(dX > 0){
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.edit_);               //swipe a destra
            backgroundColor = new ColorDrawable((ContextCompat.getColor(adapter.getContext(), R.color.purple_500)));
        }
        else {                                                                                      //swipe a sinistra
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable._delete);
            backgroundColor = new ColorDrawable(Color.RED);
        }

        assert icon != null;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX > 0) {                                                                               //swipe a destra
            int iconLeft = itemView.getLeft() + iconMargin;
            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            backgroundColor.setBounds(itemView.getLeft()+itemView.getPaddingLeft(), itemView.getTop()-itemView.getPaddingTop()*2,
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom()-itemView.getPaddingBottom());
        } else if (dX < 0) { // Swiping to the left                                                 //swipe a sinistra
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            backgroundColor.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop()-itemView.getPaddingTop(), itemView.getRight()-itemView.getPaddingRight(), itemView.getBottom()-itemView.getPaddingBottom());
        } else {                                                                                       // view is unSwiped
            backgroundColor.setBounds(0, 0, 0, 0);
        }

        backgroundColor.draw(c);
        icon.draw(c);

    }
}
