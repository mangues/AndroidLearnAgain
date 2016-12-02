package com.mangues.openGL;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by mangues on 16/12/1.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    public MyGLSurfaceView(Context context) {
        super(context);

        try
        {
            // Create an OpenGL ES 2.0 context
            setEGLContextClientVersion(2);

            // Set the Renderer for drawing on the GLSurfaceView
            setRenderer(new MyRenderer());

            // Render the view only when there is a change in the drawing
            // data
            /*

                 RENDERMODE_CONTINUOUSLY
                RENDERMODE_WHEN_DIRTY
                第一种模式（RENDERMODE_CONTINUOUSLY）：
                连续不断的刷，画完一幅图马上又画下一幅。这种模式很明显是用来画动画的；


                 第二种模式（RENDERMODE_WHEN_DIRTY）：
                 只有在需要重画的时候才画下一幅。这种模式就比较节约CPU和GPU一些，
                 适合用来画不经常需要刷新的情况。多说一句，系统如何知道需要重画了呢？当然是你要告诉它……
             */
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

            // 注意上面语句的顺序，反了可能会出错

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }

    }

    public class MyRenderer implements GLSurfaceView.Renderer
    {

        public void onSurfaceCreated(GL10 unused, EGLConfig config)
        {
            // Set the background frame color
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
        }

        public void onDrawFrame(GL10 unused)
        {
            // Redraw background color
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        }

        public void onSurfaceChanged(GL10 unused, int width, int height)
        {
            /*
            根据这里的参数width和height，我们可以知道这个宽高需要在onSurfaceChanged里得知。
             */
            GLES20.glViewport(0, 0, width, height);
        }
    }

}
