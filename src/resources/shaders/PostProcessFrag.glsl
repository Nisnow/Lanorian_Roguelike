#version 150 core

uniform sampler2D framebuffer;

in vec2 pass_TextureCoord;

out vec4 out_Color;

void main(void) 
{
	out_Color = texture(framebuffer, pass_TextureCoord);
	//out_Color = vec4(vec3(1.0 - texture(framebuffer, pass_TextureCoord)), 1.0);
}