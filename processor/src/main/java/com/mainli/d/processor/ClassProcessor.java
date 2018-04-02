package com.mainli.d.processor;

import com.google.auto.service.AutoService;
import com.mainli.d.annotations.BindView;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

@AutoService(Processor.class)
public class ClassProcessor extends AbstractProcessor {
    /**
     * ��ע�⴦���ߵ���
     *
     * @param processingEnv ����������
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    String PACK_NAME = "com.mainli.processor";

    /**
     * ������ע�ⷽ��
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Messager messager = processingEnv.getMessager();
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            if (element.getKind() == ElementKind.FIELD) {
                //��ȡ����Ԫ��
                Element enclosingElement = element.getEnclosingElement();
                PackageElement packageOf = processingEnv.getElementUtils().getPackageOf(enclosingElement);
                print("�ֶ�������-����:" + packageOf.getQualifiedName());
                print("�ֶ�������-����:" + enclosingElement.getSimpleName());
                //��ȡ�Լ�ע��Ԫ��
                BindView annotation = element.getAnnotation(BindView.class);
                int id = annotation.value();
                print("ע��-value:" + id);
                print(String.format("��ע���������:%s\n----------------------------------------------------------------------------", element.toString()));
            }
        }
        if (roundEnv.processingOver()) {
                JavaFile javaFile = JavaFile.builder(PACK_NAME,
                        TypeSpec.classBuilder("Log")
                                .addJavadoc("����$SLog��doc,Log�����ڼ�¼process����־\n",PACK_NAME)
                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                                .addField(
                                        FieldSpec.builder(String.class, "log")
                                                .addModifiers(Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
                                                .initializer("$S", mLog.toString()).build())
                                .build()).build();
                try {
                    javaFile.writeTo(processingEnv.getFiler());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                writeLog(javaFile.toString());
            }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        String canonicalName = BindView.class.getCanonicalName();
        print(String.format("����Log��:%s.Log\nʹ��ע��:%s\n----------------------------------------------------------------------------", PACK_NAME,canonicalName));
        return Collections.singleton(canonicalName);
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private StringBuffer mLog = new StringBuffer();

    /**
     * �ҵ�gradle Consoleһֱ�����´��󿴲�����־��ʹ���ļ�����¼��־
     * Caused by: java.io.IOException: CreateProcess error=2, ϵͳ�Ҳ���ָ�����ļ���
     * at java.lang.ProcessImpl.create(Native Method)
     * at java.lang.ProcessImpl.<init>(ProcessImpl.java:386)
     * at java.lang.ProcessImpl.start(ProcessImpl.java:137)
     * at java.lang.ProcessBuilder.start(ProcessBuilder.java:1029)
     * ... 87 more
     *
     * @param msg
     */
    public void print(String msg) {
        mLog.append(msg).append("\n\n\r");
    }

    final String LOG_PATH = "D:\\processor-log.txt";

    public void writeLog(String str) {
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        File file = new File(LOG_PATH);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileInputStream = new FileInputStream(file);
            fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while (-1 != (len = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.write("------------------------\n\r".getBytes());
            fileOutputStream.write(str.toString().getBytes());
            fileOutputStream.write("------------------------\n\r".getBytes());
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (Exception e) {
            }
            try {
                fileInputStream.close();
            } catch (Exception e) {
            }
        }
    }
}
