// package functions;
package pdl.backend;

import io.scif.img.ImgIOException;
import io.scif.img.ImgOpener;
import io.scif.img.ImgSaver;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.view.Views;
import net.imglib2.view.IntervalView;
import net.imglib2.loops.LoopBuilder;
import java.io.File;
import java.util.Arrays;

public class Color {
	 /*
	 	Question 2 :
	 */
	public static void brightness(Img<UnsignedByteType> img, int delta){
		final Cursor<UnsignedByteType> cursor = img.localizingCursor();

        while (cursor.hasNext()){
            cursor.fwd();
            final UnsignedByteType val = cursor.get();
			if(val.get() + delta > 255){
				val.set(255);
			} else if (val.get() + delta < 0){
				val.set(0);
			} else {
				val.set(val.get() + delta);
			}
        }
	}
	
	/*
		Question 3 :
	*/
	public static void rgbToGrey (Img<UnsignedByteType> input) {
		final RandomAccess<UnsignedByteType> r = input.randomAccess();
        final int iw = (int) input.max(0);
		final int ih = (int) input.max(1);
		final int ic = (int) input.max(2);
		
		double res = 0;
		double[] coef = {0.3, 0.59, 0.11};
        
        for (int y = 0; y <= ih; ++y) {
            for (int x = 0; x <= iw; ++x) {
				for (int channel = 0; channel <= ic; channel++) {
                	r.setPosition(x, 0);
                	r.setPosition(y, 1);
                	r.setPosition(channel, 2);
					res += r.get().get() * coef[channel];
				}
				for (int channel = 0; channel <= ic; channel++) {
                	r.setPosition(x, 0);
                	r.setPosition(y, 1);
                	r.setPosition(channel, 2);
					r.get().set((int) res);
				}
				
				res = 0;
            }
        }
	}

	/*
		Question 4 :
	*/
	public static void rgbToHsv(int r, int g, int b, float[] hsv){
		if(hsv.length != 3){
			System.out.println("Tableau mal initialisé");
			return;
		}
		float min = Math.min(r, Math.min(g, b));
		float max = Math.max(r, Math.max(g, b));
		hsv[2] = max/255;
		float tmp = 0;

		if(max == min){
			hsv[0] = 0;
			hsv[1] = 0;
		} else if(max == r){
			tmp = 60 * ((g - b)/(max - min) + 360);
			hsv[0] = tmp%360;
		} else if(max == g){
			hsv[0] = 60 * ((b - r)/(max - min)) + 120;
		} else if(max == b){
			hsv[0] = 60 * ((r - g)/(max - min)) + 240;
		}
		if(max == 0){
			hsv[1] = 0;
		} else {
			hsv[1] = 1 - min/max;
		}

		if(hsv[0] < 0 || hsv[0] > 360){
			System.out.println("h = " + hsv[0] + ", s = " + hsv[1] + ", v = " + hsv[2]);
			System.exit(-1);
		}
		if(hsv[1] < 0 || hsv[1] > 1){
			System.out.println("h = " + hsv[0] + ", s = " + hsv[1] + ", v = " + hsv[2]);
			System.exit(-1);
		}
		if(hsv[2] < 0 || hsv[2] > 1){
			System.out.println("h = " + hsv[0] + ", s = " + hsv[1] + ", v = " + hsv[2]);
			System.exit(-1);
		}
	}

	public static void hsvToRgb(double h, double s, double v, int[] rgb){
		if (h > 360) h = 360;
        if (h < 0) h = 0;
        if (s > 1) s = 1;
        if (s < 0) s = 0;
        if (v > 1) v = 1;
        if (v < 0) v = 0;

		int ti = ((int) Math.floor(h/60))%6;
		double f = h/60 - ti;
		if(f < 0 || f > 1){
			System.exit(-1);
		}
		double l = v * (1-s);
		double m = v * (1 - f * s);
		double n = v * (1 - (1 - f) * s);

		if(v < 0 || v > 1){
			System.exit(-1);
		}
		if(m < 0 || m > 1){
			System.exit(-1);
		}
		if(n < 0 || n > 1){
			System.exit(-1);
		}

		if(ti == 0){
			rgb[0] = (int) Math.round(v * 255);
			rgb[1] = (int) Math.round(n * 255);
			rgb[2] = (int) Math.round(l * 255);
		} else if(ti == 1){
			rgb[0] = (int) Math.round(m * 255);
			rgb[1] = (int) Math.round(v * 255);
			rgb[2] = (int) Math.round(l * 255);
		} else if(ti == 2){
			rgb[0] = (int) Math.round(l * 255);
			rgb[1] = (int) Math.round(v * 255);
			rgb[2] = (int) Math.round(n * 255);
		} else if(ti == 3){
			rgb[0] = (int) Math.round(l * 255);
			rgb[1] = (int) Math.round(m * 255);
			rgb[2] = (int) Math.round(v * 255);
		} else if(ti == 4){
			rgb[0] = (int) Math.round(n * 255);
			rgb[1] = (int) Math.round(l * 255);
			rgb[2] = (int) Math.round(v * 255);
		} else if(ti == 5){
			rgb[0] = (int) Math.round(v * 255);
			rgb[1] = (int) Math.round(l * 255);
			rgb[2] = (int) Math.round(m * 255);
		}

		if(rgb[0] < 0 || rgb[0] > 255){
			System.out.println("r = " + rgb[0] + ", g = " + rgb[1] + ", b = " + rgb[2]);
			System.exit(-1);
		}
		if(rgb[1] < 0 || rgb[1] > 255){
			System.out.println("r = " + rgb[0] + ", g = " + rgb[1] + ", b = " + rgb[2]);
			System.exit(-1);
		}
		if(rgb[2] < 0 || rgb[2] > 255){
			System.out.println("r = " + rgb[0] + ", g = " + rgb[1] + ", b = " + rgb[2]);
			System.exit(-1);
		}
	}

	public static void coloration(Img<UnsignedByteType> input, int val){
        if (val > 360){
            val = 360;
        }
        if (val < 0){
            val = 0;
        }
        final int value = val;

        float hsv[] = new float[3];
        int   rgb[] = new int[3];

        final IntervalView<UnsignedByteType> inputR = Views.hyperSlice(input, 2, 0);
        final IntervalView<UnsignedByteType> inputG = Views.hyperSlice(input, 2, 1);
        final IntervalView<UnsignedByteType> inputB = Views.hyperSlice(input, 2, 2);

        LoopBuilder.setImages(inputR, inputG, inputB).forEachPixel(
            (r, g, b) -> { 
                
                rgbToHsv(r.get(), g.get(), b.get(), hsv);
                hsv[0] = value;
                hsvToRgb(hsv[0], hsv[1], hsv[2], rgb);
                r.set(rgb[0]);
                g.set(rgb[1]);
                b.set(rgb[2]);
            }  
        );
    }
	
	public static void contrast1(Img<UnsignedByteType> img, int mini, int maxi){
		Img<UnsignedByteType> bw = img.copy();
        rgbToGrey(bw);
		final Cursor<UnsignedByteType> cursor = bw.localizingCursor();
		int min = 255, max = 0;
        while (cursor.hasNext()){
			cursor.fwd();
			int val = cursor.get().get();
			if(min > val){
				min = val;
			} 
			if(max < val){
				max = val;
			}
		}

		int[] LUT = new int[256];
		for(int i = 0; i < LUT.length; i++){
			LUT[i] = (255 * (i - min)) / (max - min);
			if(LUT[i] < 0) LUT[i] = 0;
			if(LUT[i] > 255) LUT[i] = 255;
		}

		final Cursor<UnsignedByteType> cursor2 = img.localizingCursor();
		while (cursor2.hasNext()){
			cursor2.fwd();
			int val = cursor2.get().get();


			if(val > maxi) val = maxi;
			if(val < mini) val = mini;
			cursor2.get().set(LUT[val]);
		}
	}

	public static void contrast2(Img<UnsignedByteType> input, int mini, int maxi){
		float hsv[] = new float[3];
        int   rgb[] = new int[3];
		float min = 1, max = 0;
		int r,g,b;
		float val;

		final RandomAccess<UnsignedByteType> rIn = input.randomAccess();
		final int iw = (int) input.max(0);
		final int ih = (int) input.max(1);

		for (int y = 0; y < ih; ++y) {
			for (int x = 0; x < iw; ++x) {
				r = getR(rIn, x, y);
				g = getG(rIn, x, y);
				b = getB(rIn, x, y);
				rgbToHsv(r, g, b, hsv);
				val = hsv[2];
				if(min > val){
					min = val;
				} 
				if(max < val){
					max = val;
				}
			}
		}

		int[] LUT = new int[256];
		for(int i = 0; i < LUT.length; i++){
			LUT[i] = Math.round((255 * (i - min * 255)) / (max * 255 - min * 255));
			if(LUT[i] < 0) LUT[i] = 0;
			if(LUT[i] > 255) LUT[i] = 255;
		}

		for (int y = 0; y < ih; ++y) {
			for (int x = 0; x < iw; ++x) {
				r = getR(rIn, x, y);
				g = getG(rIn, x, y);
				b = getB(rIn, x, y);
				rgbToHsv(r, g, b, hsv);
				hsvToRgb(hsv[0], hsv[1], LUT[Math.round(hsv[2] * 255)]/255f, rgb);
				rIn.setPosition(x, 0);
                rIn.setPosition(y, 1);
                rIn.setPosition(0, 2);
				rIn.get().set(rgb[0]);
				rIn.setPosition(1, 2);
				rIn.get().set(rgb[1]);
				rIn.setPosition(2, 2);
				rIn.get().set(rgb[2]);
			}
		}
	}


	public static void equalizer(Img<UnsignedByteType> input){
        float hsv[] = new float[3];
        int   rgb[] = new int[3];
		int[] tab = new int[256];
		Arrays.fill(tab, 0);
		float n = 0;
		int r,g,b;

		final RandomAccess<UnsignedByteType> rIn = input.randomAccess();
		final int iw = (int) input.max(0);
		final int ih = (int) input.max(1);

		for (int y = 0; y < ih; ++y) {
			for (int x = 0; x < iw; ++x) {
				r = getR(rIn, x, y);
				g = getG(rIn, x, y);
				b = getB(rIn, x, y);
				rgbToHsv(r, g, b, hsv);
				tab[Math.round(hsv[2] * 255)]++;
				n++;
			}
		}

		float[] LUT = new float[256];
		for(int i = 0; i < LUT.length; i++){
			LUT[i] = (Cumulate(i, tab) * 255) / n;
		}

		for (int y = 0; y < ih; ++y) {
			for (int x = 0; x < iw; ++x) {
				r = getR(rIn, x, y);
				g = getG(rIn, x, y);
				b = getB(rIn, x, y);
				rgbToHsv(r, g, b, hsv);
				hsvToRgb(hsv[0], hsv[1], LUT[Math.round(hsv[2] * 255)]/255f, rgb);
				rIn.setPosition(x, 0);
                rIn.setPosition(y, 1);
                rIn.setPosition(0, 2);
				rIn.get().set(rgb[0]);
				rIn.setPosition(1, 2);
				rIn.get().set(rgb[1]);
				rIn.setPosition(2, 2);
				rIn.get().set(rgb[2]);
			}
		}
	}

	public static int Cumulate(int k, int[] tab){
		int res = 0;
		for(int i =1; i <= k; i++){
			res = res + tab[i];
		}
		return res;
	}

	private static int getR(RandomAccess<UnsignedByteType> r, int x, int y){
		r.setPosition(x, 0);
        r.setPosition(y, 1);
		r.setPosition(0, 2);
		return r.get().get();
	}

	private static int getG(RandomAccess<UnsignedByteType> r, int x, int y){
		r.setPosition(x, 0);
        r.setPosition(y, 1);
		r.setPosition(1, 2);
		return r.get().get();
	}

	private static int getB(RandomAccess<UnsignedByteType> r, int x, int y){
		r.setPosition(x, 0);
        r.setPosition(y, 1);
		r.setPosition(2, 2);
		return r.get().get();
	}

	public static void meanFilterWithBorders(final Img<UnsignedByteType> input, int size) {
		final RandomAccess<UnsignedByteType> rIn = input.randomAccess();

		final int iw = (int) input.max(0);
        final int ih = (int) input.max(1);
		final int ic = (int) input.max(2);
		int res;
		int range = size/2;

		final IntervalView<UnsignedByteType> expandedView = Views.expandZero(input, range, range, 0);
		final RandomAccess<UnsignedByteType> rExp = expandedView.randomAccess();
		for(int c = 0; c < ic; c++){
			for (int x = 0; x < iw; ++x) {
				for (int y = 0; y < ih; ++y) {
					res = 0;
					for(int u = -range; u <= range; u++){
						for(int v = -range; v <= range; v++){
							rExp.setPosition(c, 2);
							rExp.setPosition(x + u, 0);
							rExp.setPosition(y + v, 1);
							res += rExp.get().get();
						}
					}
					res = res/(size * size);
					rIn.setPosition(x, 0);
					rIn.setPosition(y, 1);
					rIn.setPosition(c, 2);
					rIn.get().set(res);
				}
			}
		}
	}
	
	public static void edges(final Img<UnsignedByteType> input) {
		rgbToGrey(input);
		Img<UnsignedByteType> bw = input.copy();
		final RandomAccess<UnsignedByteType> r = input.randomAccess();
		int pixel_x;
        int pixel_y;
		int val;

		int sobel_x[][] = {{-1,0,1},
                            {-2,0,2},
                            {-1,0,1}};
 
        int sobel_y[][] = {{-1,-2,-1},
                            {0,0,0},
                            {1,2,1}};

		final int iw = (int) input.max(0);
		final int ih = (int) input.max(1);
		final int ic = (int) input.max(2);
		
		for (int x = 1; x < iw - 1; ++x) {
			for (int y = 1; y < ih - 1; ++y) {
				pixel_x = sobel(bw, x, y, sobel_x);
				pixel_y = sobel(bw, x, y, sobel_y);
				val = (int) Math.sqrt((pixel_x * pixel_x) + (pixel_y * pixel_y));
				if(val < 0) val = 0;
				else if(val > 255) val = 255;

				r.setPosition(x, 0);
				r.setPosition(y, 1);
				for(int c = 0; c <= ic; c++){
					r.setPosition(c, 2);
					r.get().set(val);
				}
			}
		}
	}

	private static int sobel(Img<UnsignedByteType> input, int pos_x, int pos_y, int[][] sobel){
		final RandomAccess<UnsignedByteType> r = input.randomAccess();
		int res = 0;
		r.setPosition(0, 2);
		for(int x = -1; x <= 1; x++){
			for(int y = -1; y <= 1; y++){
				r.setPosition(pos_x + x, 0);
				r.setPosition(pos_y + y, 1);
				res += r.get().get() * sobel[x+1][y+1];
			}
		}
		return res;
	}

	public static void gauss(final Img<UnsignedByteType> input, int gauss) {
		int size = 2 * ((int) (gauss)) + 1;
		int range = (int) size/2;
		long[][] kernel = generateKernel(gauss);
		int sommeCoef = 0;
		long coef;

		for(int i = 0; i < size; i++){
			for (int j = 0; j < size; j++) {
				sommeCoef += kernel[i][j];	
			}
		}

		final RandomAccess<UnsignedByteType> r = input.randomAccess();
		final IntervalView<UnsignedByteType> expandedView = Views.expandMirrorDouble(input, range, range, 0);
		final RandomAccess<UnsignedByteType> rEXP = expandedView.randomAccess();
		final int iw = (int) input.max(0);
		final int ih = (int) input.max(1);
		final int ic = (int) input.max(2);
		int res = 0;
		for(int c = 0; c < ic; c++){
			r.setPosition(c, 2);
			rEXP.setPosition(c, 2);
			for(int i = 0; i < iw; i++){
				for(int j = 0; j < ih; j++){
					r.setPosition(i, 0);
					r.setPosition(j, 1);
					for(int u = -range; u <= range; u++){
						for(int v = -range; v <= range; v++){
							rEXP.setPosition(i + u, 0);
							rEXP.setPosition(j + v, 1);

							coef = kernel[u+range][v+range];
							res += rEXP.get().get() * coef;
						}
					}
					res = res/(sommeCoef);
					r.get().set(res);
					
					res = 0;
				}
			}
		}
	}

	public static long[][] generateKernel(int gauss){
		int size = 2 * ((int) (gauss)) + 1;
		int range = (int) size/2;
		long[][] kernel = new long[size][size];
		double test = ((1/(2*Math.PI * Math.pow(gauss, 2))) * Math.exp(-((range * range) + (range * range))/(2 * gauss * gauss)));
		double coef = 1 / test;
		for(int i = -range; i <= range; i++){
			for(int j = -range; j <= range; j++){
				kernel[i + range][j + range] = Math.round(coef * (1/(2 * Math.PI * Math.pow(gauss, 2))) * 
				Math.exp(-((i * i) + (j * j))/(2 * gauss * gauss)));
			}
		}
		return kernel;
	}
}
