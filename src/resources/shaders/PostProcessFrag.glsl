#version 150 core

uniform vec4 tint_Color;
uniform sampler2D framebuffer;

in vec4 pass_Color;
in vec2 pass_TextureCoord;

out vec4 out_Color;

void main(void) 
{
	out_Color = pass_Color;
	// Override out_Color with our texture pixel
	out_Color = tint_Color * texture(framebuffer, pass_TextureCoord);
}