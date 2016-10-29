layout(location = 0) in vec2 in_Position;
layout(location = 1) in vec2 in_TexCoord;
out vec2 position;
void main() {
    gl_Position = ftransform();
    //gl_TexCoord[0] = gl_MultiTexCoord0;
    gl_TexCoord[0] = vec4(in_TexCoord,0,0);
    position = gl_Vertex;
}