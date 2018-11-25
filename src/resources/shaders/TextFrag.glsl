#version 150 core

uniform sampler2D texture_diffuse;
uniform vec4 textColor;

in vec4 pass_Color;
in vec2 pass_TextureCoord;

out vec4 out_Color;

void main(void) 
{
	out_Color = vec4(1.0, 1.0, 1.0, texture(texture_diffuse, pass_TextureCoord).r) * textColor;
}