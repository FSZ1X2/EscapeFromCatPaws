// Generated by view binder compiler. Do not edit!
package com.example.csi5175final.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.csi5175final.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class PlayGameBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout gameScene;

  @NonNull
  public final TextView intro;

  @NonNull
  public final Button startGameButton;

  @NonNull
  public final ImageView yarnImage;

  private PlayGameBinding(@NonNull ConstraintLayout rootView, @NonNull ConstraintLayout gameScene,
      @NonNull TextView intro, @NonNull Button startGameButton, @NonNull ImageView yarnImage) {
    this.rootView = rootView;
    this.gameScene = gameScene;
    this.intro = intro;
    this.startGameButton = startGameButton;
    this.yarnImage = yarnImage;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PlayGameBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PlayGameBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.play_game, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PlayGameBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout gameScene = (ConstraintLayout) rootView;

      id = R.id.intro;
      TextView intro = ViewBindings.findChildViewById(rootView, id);
      if (intro == null) {
        break missingId;
      }

      id = R.id.startGame_button;
      Button startGameButton = ViewBindings.findChildViewById(rootView, id);
      if (startGameButton == null) {
        break missingId;
      }

      id = R.id.yarn_image;
      ImageView yarnImage = ViewBindings.findChildViewById(rootView, id);
      if (yarnImage == null) {
        break missingId;
      }

      return new PlayGameBinding((ConstraintLayout) rootView, gameScene, intro, startGameButton,
          yarnImage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}