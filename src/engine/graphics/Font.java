package engine.graphics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.StringTokenizer;

import engine.util.IntRect;

public class Text
{
	//https://stackoverflow.com/questions/19610697/lwjgl-opengl-font-rendering
	private int lineHeight;
	private int baseLine;
	private int descent;
	private int pages;
	private Glyph[] glyphs;
	private Texture[] texPages;
	
	private class Glyph
	{
		public int chr;
		public IntRect region;
		public int xOffset, yOffset, xAdvance;
		public int[] kerning;
		public int page;
		public Texture texture;
		
		public int getKerning(int nextChar)
		{
			if(kerning == null)
				return 0;
			
			return kerning[nextChar];
		}
		
		public void updateTexture(Texture tex)
		{
			if(this.texture == null)
				this.texture = new Texture(tex.getWidth(), tex.getHeight());
			//this.texture = tex;
		}
	}
	
	public Text(String fontPath)
	{
		try
		{
			InputStream fontStream = getClass().getClassLoader().getResourceAsStream(fontPath + ".bmp");
			if (fontStream == null)
				throw new FileNotFoundException("Could not find font resource for \"" + fontPath + ".bmp\"");
			parseFont(fontStream, Charset.defaultCharset());
			
			System.out.println("Loaded font " + fontPath + ".png");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void parseFont(InputStream fontFile, Charset charset) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(fontFile, charset), 512);
		String info = br.readLine();
		String common = br.readLine();
		lineHeight = parseInt(common, "lineHeight");				
		baseLine = parseInt(common, "base");
		pages = parseInt(common, "pages");
		
		String line = "";
		ArrayList<Glyph> glyphList = null;
		int maxCodePoint = 0;
		
		while (true) 
		{
			line = br.readLine();
			if (line == null) break;
			if (line.startsWith("chars"))
			{
				System.out.println(line);
				int count = parseInt(line, "count");
				glyphList = new ArrayList<Glyph>(count);
				continue;
			}
			
			if (line.startsWith("kernings ")) 
				break;
			if (!line.startsWith("char ")) 
				continue;
			
			Glyph glyph = new Glyph();

			StringTokenizer tokens = new StringTokenizer(line, " =");
			tokens.nextToken();
			tokens.nextToken();
			
			int ch = Integer.parseInt(tokens.nextToken());
			if (ch > Character.MAX_VALUE) 
				continue;
			
			if (glyphList==null) //incase some doofus deleted a line in the font def
				glyphList = new ArrayList<Glyph>();
			
			glyphList.add(glyph);
			glyph.chr = ch;
			
			if (ch > maxCodePoint)
				maxCodePoint = ch;
			
				tokens.nextToken();
			glyph.region.x = Integer.parseInt(tokens.nextToken());
				tokens.nextToken();
			glyph.region.y = Integer.parseInt(tokens.nextToken());
				tokens.nextToken();
			glyph.region.w = Integer.parseInt(tokens.nextToken());
				tokens.nextToken();
			glyph.region.h = Integer.parseInt(tokens.nextToken());
				tokens.nextToken();
			glyph.xOffset = Integer.parseInt(tokens.nextToken());
				tokens.nextToken();
			glyph.yOffset = Integer.parseInt(tokens.nextToken());
				tokens.nextToken();
			glyph.xAdvance = Integer.parseInt(tokens.nextToken());
			
			//page ID
				tokens.nextToken();
			glyph.page = Integer.parseInt(tokens.nextToken());
			
			if (glyph.page > texPages.length)
				throw new IOException("not enough texturePages supplied; glyph "+glyph.chr+" expects page index "+glyph.page);
			
			glyph.updateTexture(texPages[glyph.page]);
			
			if (glyph.region.w > 0 && glyph.region.h > 0) 
				descent = Math.min(baseLine + glyph.yOffset, descent);
		}
	}
	
	private String parse(String line, String tag)
	{
		tag += "=";
		int start = line.indexOf(tag);
		if (start==-1)
			return "";
		
		int end = line.indexOf(' ', start+tag.length());
		
		if (end==-1)
			end = line.length();
		
		return line.substring(start+tag.length(), end);
	}
	
	private int parseInt(String line, String tag) throws IOException
	{
		try
		{
			return Integer.parseInt(parse(line, tag));
		}
		catch(NumberFormatException e)
		{
			throw new IOException("Missing/corrupt "+tag+": "+parse(line, tag));
		}
	}
}
