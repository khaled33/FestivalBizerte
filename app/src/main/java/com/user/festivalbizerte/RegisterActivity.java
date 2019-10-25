package com.user.festivalbizerte;

import android.Manifest;
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
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Model.UserInfos;
import com.user.festivalbizerte.Utils.Constants;
import com.user.festivalbizerte.Utils.FileCompressor;
import com.user.festivalbizerte.Utils.FileUtils;
import com.user.festivalbizerte.Utils.Helpers;
import com.user.festivalbizerte.Utils.Loader;
import com.user.festivalbizerte.WebService.WebService;

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
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    Context context;
    @BindView(R.id.regName)
    TextInputEditText Nom;
    @BindView(R.id.regPrenom)
    TextInputEditText Prenom;
    @BindView(R.id.regMail)
    TextInputEditText Email;
    @BindView(R.id.tel)
    TextInputEditText Tel;
    @BindView(R.id.regPassword)
    TextInputEditText Password;
    @BindView(R.id.regPassword2)
    TextInputEditText ConfiremPassword;

    @BindView(R.id.input_layout_nom)
    TextInputLayout layoutNom;
    @BindView(R.id.input_layout_prenom)
    TextInputLayout layoutPrenom;
    @BindView(R.id.input_layout_email)
    TextInputLayout layoutEmail;
    @BindView(R.id.input_layout_tel)
    TextInputLayout layoutTel;
    @BindView(R.id.input_layout_password)
    TextInputLayout layoutPassword;
    @BindView(R.id.input_layout_confpassword)
    TextInputLayout layoutConfirmePassword;

    @BindView(R.id.regUserPhoto)
    ImageView Photo;
    String email, password, nom, prenom, tel, confiremPassword;
    Uri imageUri = null;
    private FileCompressor mCompressor;
    private File mPhotoFile;
    BottomSheetDialog mBottomSheetDialog;
    View DialogBottomView = null;
    DialogFragment Loding = Loader.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        context = this;
        mCompressor = new FileCompressor(context);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build())).build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.txtlogin)
    public void LoginPage() {
        startActivity(new Intent(context, LoginActivity.class));
    }

    @OnClick(R.id.regBtn)
    public void Inscrire() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        nom = Nom.getText().toString().trim();
        prenom = Prenom.getText().toString().trim();
        confiremPassword = ConfiremPassword.getText().toString().trim();
        tel = Tel.getText().toString().trim();
        if (Valider()) {
            if (Helpers.isConnected(context)) {
                Loding.show(getSupportFragmentManager(), Constants.LODING);
                MultipartBody.Part part = null;
                if (imageUri != null) {
                    part = prepareFilePart(imageUri);
                }
                Call<RSResponse> callUpload = WebService.getInstance().getApi().inscrireUser(
                        part,
                        createPartFormString(nom),
                        createPartFormString(prenom),
                        createPartFormString(tel),
                        createPartFormString(email),
                        createPartFormString(password)
                );
                callUpload.enqueue(new Callback<RSResponse>() {
                    @Override
                    public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                        Loding.dismiss();
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                startActivity(new Intent(context, LoginActivity.class));
                            } else if (response.body().getStatus() == 0) {
                                Toast.makeText(context, "err", Toast.LENGTH_SHORT).show();
                            } else if (response.body().getStatus() == 2) {
                                Toast.makeText(context, getString(R.string.EmailOuMotDePasseInvalide), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RSResponse> call, Throwable t) {
                        Loding.dismiss();
                        Log.i("err", t.getMessage());
                    }
                });
            } else {
                Helpers.ShowMessageConnection(context);
            }
        }
    }

    private boolean Valider() {
        boolean valide = true;
        if (email.isEmpty()) {
            layoutEmail.setError(getString(R.string.champs_obligatoir));
            valide = false;
        } else {
            layoutEmail.setError(null);
        }
        if (!email.isEmpty() && (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            layoutEmail.setError(getString(R.string.email_invalide));
            valide = false;
        }
        if (nom.isEmpty()) {
            layoutNom.setError(getString(R.string.champs_obligatoir));
            valide = false;
        } else {
            layoutNom.setError(null);
        }
        if (prenom.isEmpty()) {
            layoutPrenom.setError(getString(R.string.champs_obligatoir));
            valide = false;
        } else {
            layoutPrenom.setError(null);
        }
        if (tel.isEmpty()) {
            layoutTel.setError(getString(R.string.champs_obligatoir));
            valide = false;
        } else {
            layoutTel.setError(null);
        }
        if (confiremPassword.isEmpty()) {
            layoutConfirmePassword.setError(getString(R.string.champs_obligatoir));
            valide = false;
        } else {
            layoutConfirmePassword.setError(null);
        }
        if (password.isEmpty()) {
            layoutPassword.setError(getString(R.string.champs_obligatoir));
            valide = false;
        } else {
            layoutPassword.setError(null);
        }
        if (!password.isEmpty() && !confiremPassword.isEmpty() && !password.equals(confiremPassword)) {
            layoutConfirmePassword.setError("password n'ai pas identique ");
            valide = false;
        }
        if (imageUri == null) {
            Toast.makeText(context, "Choisir une image", Toast.LENGTH_SHORT).show();
            valide = false;
        }
        return valide;
    }

    @OnClick(R.id.regUserPhoto)
    public void ChooseImage() {
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

    private void openGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, Constants.REQUEST_GALLERY_PHOTO);
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
                Photo.setImageURI(imageUri);
                mBottomSheetDialog.dismiss();
            } else if (requestCode == Constants.REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), BitmapFactory.decodeFile(mPhotoFile.getAbsolutePath()), "Image Description", null);
                imageUri = Uri.parse(path);
                Photo.setImageURI(imageUri);
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
        if (requestCode == Constants.REQUEST_PERMISSION_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        } else if (requestCode == Constants.REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            }
        }
    }

    private RequestBody createPartFormString(String value) {
        return RequestBody.create(MultipartBody.FORM, value);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private MultipartBody.Part prepareFilePart(Uri fileUri) {
        File file = FileUtils.getFile(context, fileUri);
        RequestBody requestBody = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), file);
        return MultipartBody.Part.createFormData("image", file.getName(), requestBody);
    }

}
