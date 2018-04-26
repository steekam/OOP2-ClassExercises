
import java.util.concurrent.atomic.AtomicInteger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author steekam
 */
public class Generator {
   private static final AtomicInteger count = new AtomicInteger(1560);
   private static final Generator generator = new Generator();

  private Generator() { }
  public static Generator getInstance() {
    return generator;
  }

  public static int generate() {
    return count.getAndIncrement();
  }
}
