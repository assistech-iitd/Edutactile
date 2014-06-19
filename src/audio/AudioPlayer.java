/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package audio;

import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import java.io.Serializable;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.ugens.Envelope;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.WavePlayer;

/**
 *
 * @author DELL
 */
public class AudioPlayer implements Runnable
{
    private volatile boolean running;
    public AudioPlayer() 
    {
        this.running = true;
    }
    
    @Override
    public void run() 
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        this.playAudio();
    }
    
    public void terminate()
    {
        this.running = false;
    }
    
    private synchronized void playAudio()
    {
        if (this.running) 
        {
            AudioContext ac;
            ac = new AudioContext();
            Envelope freqEnv = new Envelope(ac, 500);
            WavePlayer wp = new WavePlayer(ac, freqEnv, Buffer.SINE);
            freqEnv.addSegment(2000, 2000);
            Gain g = new Gain(ac, 4, 0.1f);
            g.addInput(wp);
            ac.out.addInput(g);
            ac.start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            ac.stop();
//            this.terminate();
        }
    }
}
