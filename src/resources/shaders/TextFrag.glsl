#version 150 core

in vec2 pass_TextureCoords;

out vec4 out_Color;

uniform sampler2D texture_diffuse;
uniform vec3 textColor;

void main(void)
{
	vec4 sampled = vec4(1.0, 1.0, 1.0, texture(texture_diffuse, pass_TextureCoords).r);
	out_Color = vec4(textColor, 1.0) * sampled;
}