#version 450

layout(location = 0) in vec3 pos;
layout(location = 1) in vec2 tex;
layout(location = 2) in vec3 normal;

layout(location = 0) out vec2 texO;
layout(location = 1) out vec3 sNormal;
layout(location = 2) out vec3 ligVec;
layout(location = 3) out float visiblity;

uniform mat4 transform;
uniform mat4 projection;
uniform mat4 view;
uniform vec3 ligPos;
uniform float density;
uniform float gradient;

void main() {
    vec4 worldPos = transform * vec4(pos,1.0f);
    vec4 relPos = view * worldPos;

    gl_Position = projection * relPos;

    texO = tex;

    sNormal = (transform * vec4(normal,0.0f)).xyz;
    ligVec = ligPos - worldPos.xyz;

    float deltaX = length(relPos.xyz);

    visiblity = exp(-pow(deltaX*density,gradient));
}
