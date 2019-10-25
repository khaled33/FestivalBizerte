package com.user.festivalbizerte;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Model.UserInfos;
import com.user.festivalbizerte.Utils.Constants;
import com.user.festivalbizerte.Utils.FileCompressor;
import com.user.festivalbizerte.Utils.FileUtils;
import com.user.festivalbizerte.Utils.Helpers;
import com.user.festivalbizerte.WebService.Urls;
import com.user.festivalbizerte.WebService.WebService;
import com.user.festivalbizerte.session.RSSession;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.nomPrenom)
    TextView NomPrenom;
    @BindView(R.id.Email)
    TextView Email;
    @BindView(R.id.tel)
    TextView Tel;
    @BindView(R.id.score)
    TextView Score;
    @BindView(R.id.img_profil)
    SimpleDraweeView photo;
    SimpleDraweeView photo_modifier;
    UserInfos userInfos;
    AlertDialog alertDialog;
    BottomSheetDialog mBottomSheetDialog;
    View popupInputDialogView = null, DialogBottomView = null;
    TextInputEditText nomEditText, prenomEditText, passwordEditText, phoneEdiText;
    String password, prenom, nom, tel;
    private FileCompressor mCompressor;
    private File mPhotoFile;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        context = this;
        ButterKnife.bind(this);
        mCompressor = new FileCompressor(context);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        setUser(RSSession.getLocalStorage(context), Constants.PROFIL);
        GetScoreFinal();

    }
    private void GetScoreFinal() {
        Call<RSResponse> callUpload = WebService.getInstance().getApi().getScore(RSSession.getIdUser(context));
        callUpload.enqueue(new Callback<RSResponse>() {
            @Override
            public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        UserInfos tab = new Gson().fromJson(new Gson().toJson(response.body().getData()), UserInfos.class);
                        Score.setText(String.valueOf(tab.getScore_final()));
                    } else if (response.body().getStatus() == 0) {
                        Toast.makeText(getApplicationContext(), "errr", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RSResponse> call, Throwable t) {
                Log.d("err", t.getMessage());
            }
        });
    }
    private void setUser(UserInfos userInfos, String TAG) {
        if (userInfos != null) {
            switch (TAG) {
                case Constants.PROFIL:
                    NomPrenom.setText(String.format("%s %s", userInfos.getNom(), userInfos.getPrenom()));
                    Email.setText(userInfos.getEmail());
                    Tel.setText(userInfos.getTel());
//                    Score.setText(String.valueOf(userInfos.getScore_final()));
                    if (userInfos.getPhoto() != null) {
                        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
                        roundingParams.setBorder(getResources().getColor(R.color.white), 4f);
                        roundingParams.setRoundAsCircle(true);
                        photo.getHierarchy().setRoundingParams(roundingParams);
                        photo.setImageURI(Urls.IMAGE_PROFIL + userInfos.getPhoto());
                    } else {
                        photo.setImageResource(R.drawable.userphoto);
                    }
                    break;
                case Constants.EDITE_PROFIL:
                    nomEditText.setText(userInfos.getNom());
                    prenomEditText.setText(userInfos.getPrenom());
                    passwordEditText.setText(userInfos.getPassword());
                    phoneEdiText.setText(userInfos.getTel());
                    if (userInfos.getPhoto() != null) {
                        RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
                        roundingParams.setBorder(getResources().getColor(R.color.colorPrimary), 4f);
                        roundingParams.setRoundAsCircle(true);
                        photo_modifier.getHierarchy().setRoundingParams(roundingParams);
                        photo_modifier.setImageURI(Urls.IMAGE_PROFIL + userInfos.getPhoto());
                    } else {
                        photo_modifier.setImageResource(R.drawable.userphoto);
                    }
                    break;
            }
        } else {
            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.modifier)
    public void Modifer() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);
        initPopupViewControls();
        alertDialogBuilder.setView(popupInputDialogView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /* Initialize popup dialog view and ui controls in the popup dialog. */
    private void initPopupViewControls() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popupInputDialogView = layoutInflater.inflate(R.layout.profil_dialog_update, null);

        nomEditText = popupInputDialogView.findViewById(R.id.input_nom);
        prenomEditText = popupInputDialogView.findViewById(R.id.input_prenom);
        phoneEdiText = popupInputDialogView.findViewById(R.id.input_tel);
        passwordEditText = popupInputDialogView.findViewById(R.id.input_password);
        photo_modifier = popupInputDialogView.findViewById(R.id.img_profil);
        setUser(RSSession.getLocalStorage(context), Constants.EDITE_PROFIL);
    }

    public void openGalleryProfil(View view) {
        mBottomSheetDialog = new BottomSheetDialog(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        DialogBottomView = layoutInflater.inflate(R.layout.bottom_dialog, null);
        mBottomSheetDialog.setContentView(DialogBottomView);
        mBottomSheetDialog.show();
    }

    public void Camera(View view) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_PERMISSION_CAMERA);
            }
        } else {
            openCamera();
        }
    }

    public void Gallery(View view) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_PERMISSION_STORAGE);
            }
        } else {
            openGallery();
        }
    }

    private void openCamera() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            mPhotoFile = photoFile;
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(pictureIntent, Constants.REQUEST_TAKE_PHOTO);
        }
    }

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, Constants.REQUEST_GALLERY_PHOTO);
    }


    public void updateProfile(View view) {
        password = passwordEditText.getText().toString().trim();
        nom = nomEditText.getText().toString().trim();
        prenom = prenomEditText.getText().toString().trim();
        tel = phoneEdiText.getText().toString().trim();
        if (Valider()) {
            if (Helpers.isConnected(context)) {
                MultipartBody.Part part = null;
                if (imageUri != null) {
                    part = prepareFilePart(imageUri);
                }
                Call<RSResponse> callUpload = WebService.getInstance().getApi().updateUser(
                        part,
                        createPartFormString(nom),
                        createPartFormString(prenom),
                        createPartFormString(tel),
                        createPartFormString(password),
                        createPartFormString(String.valueOf(RSSession.getLocalStorage(context).getId_user()))
                );
                callUpload.enqueue(new Callback<RSResponse>() {
                    @Override
                    public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                        //loading.dismiss();
                        if (response.body().getStatus() == 1) {
                            alertDialog.cancel();
                            ImagePipeline imagePipeline = Fresco.getImagePipeline();
                            imagePipeline.clearMemoryCaches();
                            imagePipeline.clearDiskCaches();
                            imagePipeline.clearCaches();
                            RSSession.saveIntoSharedPreferences(response.body().getData(), context);
                            setUser(RSSession.getLocalStorage(context), Constants.PROFIL);
                        } else if (response.body().getStatus() == 0) {
                            Toast.makeText(getApplicationContext(), "err", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RSResponse> call, Throwable t) {
                        //loading.dismiss();
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Helpers.ShowMessageConnection(context);
            }
        }
    }

    private boolean Valider() {
        boolean valide = true;
        if (password.isEmpty()) {
            passwordEditText.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (nom.isEmpty()) {
            nomEditText.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (prenom.isEmpty()) {
            passwordEditText.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (tel.isEmpty()) {
            phoneEdiText.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        return valide;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()), "Image Description", null);
                imageUri = Uri.parse(path);
                photo_modifier.setImageURI(imageUri);
                mBottomSheetDialog.dismiss();
            } else if (requestCode == Constants.REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()), "Image Description", null);
                imageUri = Uri.parse(path);
                photo_modifier.setImageURI(imageUri);
                mBottomSheetDialog.dismiss();
            }
        }
    }

    private String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] project = {MediaStore.Images.Media.DATA};
            cursor = getApplicationContext().getContentResolver().query(contentUri, project, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        } else if (requestCode == Constants.REQUEST_PERMISSION_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private MultipartBody.Part prepareFilePart(Uri fileUri) {
        File file = FileUtils.getFile(context, fileUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        return MultipartBody.Part.createFormData("image", file.getName(), requestBody);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private RequestBody createPartFormString(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }

    public void retour(View view) {
        startActivity(new Intent(context, MainActivity.class));
    }

    public void annulerDialog(View view) {
        alertDialog.cancel();
    }

}
