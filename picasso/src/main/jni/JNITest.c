//
// Created by Administrator on 2018/1/9.
//

#include <string.h>
#include <jni.h>

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */
jstring Java_com_squareup_jnisam_JNITest_getHello(JNIEnv* env, jobject thiz){
    return (*env)->NewStringUTF(env, "Hello, This is a JNI Test !");
}
