/**
 * @author Xuyu Fu
 * @version 1.0
 * @description 单例模式的实现
 * @date 2022/2/18 20:27
 */
public class SingletonPattern {
    public static void main(String[] args) {
        System.out.println(Singleton1.getInstance() == Singleton1.getInstance());

        System.out.println(Singleton2.getInstance() == Singleton2.getInstance());

        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                System.out.println(Singleton3.getInstance() == Singleton3.getInstance());
            }, "test03").start();
        }

        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                System.out.println(Singleton4.getInstance() == Singleton4.getInstance());
            }, "test03").start();
        }
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                System.out.println(Singleton5.getInstance() == Singleton5.getInstance());
            }, "test03").start();
        }
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                System.out.println(Singleton6.getInstance() == Singleton6.getInstance());
            }, "test03").start();
        }

        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                System.out.println(Singleton7.getInstance() == Singleton7.getInstance());
            }, "test03").start();
        }
    }
}

/**
 * 恶汉（静态常量）可用
 * 优点：这种写法比较简单，就是在类装载的时候就完成实例化。避免了线程同步问题。
 * 缺点：在类装载的时候就完成实例化，没有达到Lazy Loading的效果。如果从始至终从未使用过这个实例，则会造成内存的浪费
 */
class Singleton1 {
    private final static Singleton1 INSTANCE = new Singleton1();

    private Singleton1() {
    }

    public static Singleton1 getInstance() {
        return INSTANCE;
    }
}

/**
 * 恶汉（静态代码块）可用
 * 这种方式和上面的方式其实类似，只不过将类实例化的过程放在了静态代码块中，也是在类装载的时候，就执行静态代码块中的代码，初始化类的实例。优缺点和上面是一样的。
 */
class Singleton2 {
    private static Singleton2 singleton2 = null;

    static {
        singleton2 = new Singleton2();
    }

    private Singleton2() {
    }

    public static Singleton2 getInstance() {
        return singleton2;
    }
}

/**
 * 懒汉式（线程不安全）
 * 这种写法起到了Lazy Loading的效果，但是只能在单线程下使用。如果在多线程下，一个线程进入了if (singleton == null)判断语句块，
 * 还未来得及往下执行，另一个线程也通过了这个判断语句，这时便会产生多个实例。所以在多线程环境下不可使用这种方式
 */

class Singleton3 {
    private static Singleton3 singleton3;

    private Singleton3() {
    }

    public static Singleton3 getInstance() {
        if (singleton3 == null) {
            singleton3 = new Singleton3();
        }
        return singleton3;
    }
}

/**
 * 懒汉式（线程安全，同步方法）【不推荐用】
 * 解决上面第三种实现方式的线程不安全问题，做个线程同步就可以了，于是就对getInstance()方法进行了线程同步。
 * 缺点：效率太低了，每个线程在想获得类的实例时候，执行getInstance()方法都要进行同步。而其实这个方法只执行一次实例化代码就够了，后面的想获得该类实例，直接return就行了。方法进行同步效率太低要改进。
 */
class Singleton4 {
    private static Singleton4 singleton4;

    private Singleton4() {
    }

    public static synchronized Singleton4 getInstance() {
        if (singleton4 == null) {
            singleton4 = new Singleton4();
        }
        return singleton4;
    }
}

/**
 * 懒汉式（线程安全，同步代码块）【不可用】
 * 由于第四种实现方式同步效率太低，所以摒弃同步方法，改为同步产生实例化的的代码块。
 * 但是这种同步并不能起到线程同步的作用。跟第3种实现方式遇到的情形一致，
 * 假如一个线程进入了if (singleton == null)判断语句块，还未来得及往下执行，另一个线程也通过了这个判断语句，这时便会产生多个实例。
 */

class Singleton5 {
    private static Singleton5 singleton5;

    private Singleton5() {
    }

    public static Singleton5 getInstance() {
        if (singleton5 == null) {
            synchronized (Singleton5.class) {
                singleton5 = new Singleton5();
            }
        }
        return singleton5;
    }
}


/**
 * 双重检查【推荐使用】
 * Double-Check概念对于多线程开发者来说不会陌生，如代码中所示，我们进行了两次if (singleton == null)检查，
 * 这样就可以保证线程安全了。这样，实例化代码只用执行一次，后面再次访问时，判断if (singleton == null)，直接return实例化对象。
 * 优点：线程安全；延迟加载；效率较高
 */
class Singleton6 {
    private static Singleton6 singleton6;

    private Singleton6() {
    }

    public static synchronized Singleton6 getInstance() {
        if (singleton6 == null) {
            synchronized (Singleton6.class) {
                if (singleton6 == null)
                    singleton6 = new Singleton6();
            }
        }
        return singleton6;
    }
}

/**
 * 静态内部类【推荐使用】
 * 这种方式跟饿汉式方式采用的机制类似，但又有不同。两者都是采用了类装载的机制来保证初始化实例时只有一个线程。
 * 不同的地方在饿汉式方式是只要Singleton类被装载就会实例化，没有Lazy-Loading的作用，
 * 而静态内部类方式在Singleton类被装载时并不会立即实例化，而是在需要实例化时，调用getInstance方法，才会装载SingletonInstance类，从而完成Singleton的实例化。
 * 类的静态属性只会在第一次加载类的时候初始化，所以在这里，JVM帮助我们保证了线程的安全性，在类进行初始化时，别的线程是无法进入的。
 * 优点：避免了线程不安全，延迟加载，效率高。

 */
class Singleton7{
    private Singleton7(){}
    private static class SingletonInstance{
        private static final Singleton7 singleton7 = new Singleton7();
    }
    public static Singleton7 getInstance() {
        return SingletonInstance.singleton7;
    }
}

/**
 * 枚举【推荐使用】
 * 借助JDK1.5中添加的枚举来实现单例模式。不仅能避免多线程同步问题，而且还能防止反序列化重新创建新的对象。
 */
enum Singleton8{
    INSTANCE;
    public void whateverMethod() {

    }
}