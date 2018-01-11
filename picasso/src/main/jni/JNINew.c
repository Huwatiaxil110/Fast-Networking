#include <jni.h>
#include <android/log.h>
#include <string.h>

#define  LOG_TAG    "XXXX"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,  LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

jstring Java_com_squareup_jnisam_JNINew_getTian(JNIEnv* env, jobject thiz){
    LOGE("------------------------------------------------------------");
    LOGE("-----  New.New New.New New.New New.New... -------");

    return (*env)->NewStringUTF(env, "New, New is a New New Test !");
}

