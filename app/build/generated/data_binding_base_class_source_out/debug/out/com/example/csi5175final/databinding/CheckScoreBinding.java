// Generated by view binder compiler. Do not edit!
package com.example.csi5175final.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
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

public final class CheckScoreBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button allUserRecord;

  @NonNull
  public final Button leaveScorePageButton;

  @NonNull
  public final Button playerRecord;

  @NonNull
  public final TextView scoreRecords;

  @NonNull
  public final ScrollView scoreRecordsPage;

  @NonNull
  public final TextView userScoreBoard;

  private CheckScoreBinding(@NonNull ConstraintLayout rootView, @NonNull Button allUserRecord,
      @NonNull Button leaveScorePageButton, @NonNull Button playerRecord,
      @NonNull TextView scoreRecords, @NonNull ScrollView scoreRecordsPage,
      @NonNull TextView userScoreBoard) {
    this.rootView = rootView;
    this.allUserRecord = allUserRecord;
    this.leaveScorePageButton = leaveScorePageButton;
    this.playerRecord = playerRecord;
    this.scoreRecords = scoreRecords;
    this.scoreRecordsPage = scoreRecordsPage;
    this.userScoreBoard = userScoreBoard;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CheckScoreBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CheckScoreBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.check_score, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CheckScoreBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.allUser_record;
      Button allUserRecord = ViewBindings.findChildViewById(rootView, id);
      if (allUserRecord == null) {
        break missingId;
      }

      id = R.id.leave_scorePage_button;
      Button leaveScorePageButton = ViewBindings.findChildViewById(rootView, id);
      if (leaveScorePageButton == null) {
        break missingId;
      }

      id = R.id.player_record;
      Button playerRecord = ViewBindings.findChildViewById(rootView, id);
      if (playerRecord == null) {
        break missingId;
      }

      id = R.id.score_records;
      TextView scoreRecords = ViewBindings.findChildViewById(rootView, id);
      if (scoreRecords == null) {
        break missingId;
      }

      id = R.id.score_records_page;
      ScrollView scoreRecordsPage = ViewBindings.findChildViewById(rootView, id);
      if (scoreRecordsPage == null) {
        break missingId;
      }

      id = R.id.user_score_board;
      TextView userScoreBoard = ViewBindings.findChildViewById(rootView, id);
      if (userScoreBoard == null) {
        break missingId;
      }

      return new CheckScoreBinding((ConstraintLayout) rootView, allUserRecord, leaveScorePageButton,
          playerRecord, scoreRecords, scoreRecordsPage, userScoreBoard);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
