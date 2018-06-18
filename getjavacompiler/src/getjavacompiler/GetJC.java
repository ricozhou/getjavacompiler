package getjavacompiler;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

//��ȡjava������
public class GetJC {
	public static void main(String[] args) throws Exception {
		System.out.println(getJavaCompiler1());
		System.out.println(getJavaCompiler2());
		System.out.println(getJavaCompiler3("E:\\Java\\tools.jar"));
	}

	// ��һ�֣�ʹ��ϵͳ������ȡ
	public static JavaCompiler getJavaCompiler1() {
		// ��Ҫ��jdk/lib�µ�tools.jar���Ƶ�jdk/jre/lib��
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		return compiler;
	}

	// �ڶ��֣�ʹ�û���������jdk�ж�ȡ
	public static JavaCompiler getJavaCompiler2() throws Exception {
		String javahome = System.getenv("JAVA_HOME");
		File file = new File(javahome + File.separator + "lib\\tools.jar");
		if (!file.exists()) {
			return null;
		}
		JavaCompiler compiler = getJavaCompilerByLocation(file);
		return compiler;
	}

	// �����֣�����Ŀ¼��tools.jar��ȡ
	public static JavaCompiler getJavaCompiler3(String filePath) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		JavaCompiler compiler = getJavaCompilerByLocation(file);
		return compiler;
	}

	// ��ȡ������
	public static JavaCompiler getJavaCompilerByLocation(File f1) throws Exception {
		String p = f1.getAbsolutePath();
		URL[] urls = new URL[] { new URL("file:/" + p) };
		URLClassLoader myClassLoader1 = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
		Class<?> myClass1 = myClassLoader1.loadClass("com.sun.tools.javac.api.JavacTool");
		JavaCompiler compiler = myClass1.asSubclass(JavaCompiler.class).asSubclass(JavaCompiler.class).newInstance();
		return compiler;
	}

}
