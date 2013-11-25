
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;


public class ModifyVersion {

  /**
   * @param args
   */
  static String mWantedName;
  static String mTargetName;
  static String version;
  static boolean mHaveDeleteCameraFilter = false;
  static boolean mFindCameraFilterXXX = false;

  public static void main( String[] args ) throws Throwable {

    try {

      File file = new File( args[0] );

      if ( args.length == 2 ) {
       
        version = new String( args[1] );
        EditFileAndDirectoryName( file );
      } // end if we got right number of args
      else {
        System.out.printf( "using : java Refector projectpath version" );
      } // end else we got else or no args

    } // end try
    catch ( Throwable t ) {
      System.out.printf( "using : java Refector projectpath version" );
      t.printStackTrace();
    } // end catch
  } // end main()

  static void EditFileAndDirectoryName( File dir ) throws Throwable {

    File[] fileAndDir = dir.listFiles();

    for ( File aFileOrDir : fileAndDir ) {

      String fileName = aFileOrDir.getName();
      
      if ( fileName.equalsIgnoreCase( "information.htm" ) || fileName.equalsIgnoreCase( "fr_information.htm" ) ) {

        FileReader fReader = new FileReader( aFileOrDir );
        FileWriter fWriter = new FileWriter( aFileOrDir.getAbsolutePath() + ".mod" );
        int in = 0;
        char[] wlnChar = { '\n' };
        boolean done = false;
        String buffer = new String();

        while ( ( in = fReader.read() ) != -1 ) {

          if ( !done ) {

            if ( in == '\n' ) {

              if ( buffer.contains( "<strong>Version:</strong>" ) ) {
                buffer = buffer.substring( 0, buffer.indexOf( "> " )+2 ) + "1.0.0." + version ;
              }

              fWriter.write( buffer + "\n" );
              buffer = new String();
            }
            else {
              buffer = buffer.concat( new String( (char) in + "" ) );
            }

          } // end not done
          else {
            fWriter.write( in );
          }

        }

        if ( buffer.length() != 0 )
          fWriter.write( buffer );

        fReader.close();
        fWriter.close();
        aFileOrDir.delete();
        aFileOrDir = new File( aFileOrDir.getAbsolutePath() + ".mod" );
        aFileOrDir.renameTo( new File( aFileOrDir.getAbsolutePath().substring( 0, aFileOrDir.getAbsolutePath().length() - 3 ) ) );
      } // end if we got build.properties
      else if ( aFileOrDir.isDirectory() ) {

        EditFileAndDirectoryName( aFileOrDir );

      } // end else if we got a dir
    } // end for
  } // end EditFileAndDirectoryName()
} // end class
