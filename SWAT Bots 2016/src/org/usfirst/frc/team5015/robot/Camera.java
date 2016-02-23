package org.usfirst.frc.team5015.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;


public class Camera implements Runnable {
int session;
Image frame;
boolean enabled = true;

  public Camera()
  {
	  frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);

	  // the camera name (ex "cam0") can be found through the roborio web interface
	  session = NIVision.IMAQdxOpenCamera("cam0",
	          NIVision.IMAQdxCameraControlMode.CameraControlModeController);
	  NIVision.IMAQdxConfigureGrab(session);
	  enabled = true;
  }
  
  public void terminate()
  {
	  enabled = false;
  }
  
  public void run()
  {
	  NIVision.IMAQdxStartAcquisition(session);

      /**
       * grab an image, draw the circle, and provide it for the camera server
       * which will in turn send it to the dashboard.
       */
     // NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
      
      while(enabled == true)
      {
          NIVision.IMAQdxGrab(session, frame, 1);
         /* NIVision.imaqDrawShapeOnImage(frame, frame, rect,
                  DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);*/
          
          CameraServer.getInstance().setImage(frame);
      }
  }
}
