#include <jni.h>
#include <android/log.h>
#include <string.h>

#define  LOG_TAG    "XXXX"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,  LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

jstring Java_com_squareup_jnisam_JNITest_getHello(JNIEnv* env, jobject thiz){
    LOGE("------------------------------------------------------------");
    LOGE("-----  This is a log test1 -------");

    return (*env)->NewStringUTF(env, "Hello, This is a JNI Test !");
}
