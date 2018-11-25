#version 150 core

in vec3 in_Position;
in vec4 in_Color;

out vec4 pass_Color;

uniform mat4 view;

void main(void) 
{
	gl_Position = view * vec4(in_Position, 1.0);
	pass_Color = in_Color;
}