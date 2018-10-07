#version 150 core

in vec3 in_Position;
in vec4 in_Color;
in vec2 in_TextureCoord;

out vec4 pass_Color;
out vec2 pass_TextureCoord;

uniform mat4 transform;
uniform mat4 view;

void main(void) 
{
	gl_Position = view * transform * vec4(in_Position, 1.0);
	pass_Color = in_Color;
	pass_TextureCoord = in_TextureCoord;
}