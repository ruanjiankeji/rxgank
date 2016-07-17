/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.wy.retrofit.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.bumptech.glide.Glide;
import com.wy.retrofit.kjhttp.rxjava.RetrofitClient;
import rx.Observable;

public class Shares {

  private static void shareImage(Context context, Uri uri, String title) {
    Intent shareIntent = new Intent();
    shareIntent.setAction(Intent.ACTION_SEND);
    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
    shareIntent.setType("image/jpeg");
    context.startActivity(Intent.createChooser(shareIntent, title));
  }

  public static void share(Context context, String subject, String data) {
    Intent intent = new Intent(Intent.ACTION_SEND);
    intent.setType("text/plain");
    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    intent.putExtra(Intent.EXTRA_TEXT, data);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(Intent.createChooser(intent, "分享"));
  }


  public static void shareImage(Context context, String uri, String title) {
    Observable.just(uri)
        .map(url -> Glide.with(context).load(uri).downloadOnly(1080, 1920))
        .flatMap(Observable::from)
        .map(Uri::fromFile)
        .compose(RetrofitClient.applySchedulers())
        .subscribe(uri1 -> Shares.shareImage(context, uri1, title));
  }


}
