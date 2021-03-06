// Generated by view binder compiler. Do not edit!
package com.example.csi5175final.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.csi5175final.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class HomePageBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button checkScores;

  @NonNull
  public final Button editing;

  @NonNull
  public final FloatingActionButton fab;

  @NonNull
  public final ImageView gameplayImage;

  @NonNull
  public final Button references;

  @NonNull
  public final Button start;

  private HomePageBinding(@NonNull ConstraintLayout rootView, @NonNull Button checkScores,
      @NonNull Button editing, @NonNull FloatingActionButton fab, @NonNull ImageView gameplayImage,
      @NonNull Button references, @NonNull Button start) {
    this.rootView = rootView;
    this.checkScores = checkScores;
    this.editing = editing;
    this.fab = fab;
    this.gameplayImage = gameplayImage;
    this.references = references;
    this.start = start;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static HomePageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static HomePageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.home_page, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static HomePageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.checkScores;
      Button checkScores = ViewBindings.findChildViewById(rootView, id);
      if (checkScores == null) {
        break missingId;
      }

      id = R.id.editing;
      Button editing = ViewBindings.findChildViewById(rootView, id);
      if (editing == null) {
        break missingId;
      }

      id = R.id.fab;
      FloatingActionButton fab = ViewBindings.findChildViewById(rootView, id);
      if (fab == null) {
        break missingId;
      }

      id = R.id.gameplay_image;
      ImageView gameplayImage = ViewBindings.findChildViewById(rootView, id);
      if (gameplayImage == null) {
        break missingId;
      }

      id = R.id.references;
      Button references = ViewBindings.findChildViewById(rootView, id);
      if (references == null) {
        break missingId;
      }

      id = R.id.start;
      Button start = ViewBindings.findChildViewById(rootView, id);
      if (start == null) {
        break missingId;
      }

      return new HomePageBinding((ConstraintLayout) rootView, checkScores, editing, fab,
          gameplayImage, references, start);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
