#version 450

layout(location = 0) in vec2 col;

layout(location = 0) out vec4 colO;

uniform sampler2D sampler;

void main() {
    colO = texture(sampler,col);
    //colO = vec4(1.0f,0.0f,0.0f,1.0f);
}
