#ifdef GL_ES
precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords;

uniform sampler2D u_texture;

uniform float time;
uniform vec2 resolution;

vec3 hsv2rgb(vec3 c) {//https://github.com/hughsk/glsl-hsv2rgb
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main()
{
    vec2 p = ( v_texCoords.xy );

    //p = p*2000.0;
    //p.x -= resolution.x;
    //p.y -= resolution.y;
    //p.x *= resolution.x / resolution.y;

	float color =
    		(sin(p.x/125.0-time)+1.0)
    	      + (sin(p.y/125.0-time)+1.0)
    	      + (sin(p.x+p.y)+1.0)
    		;

    	color = mod(color,4.0)+time;

    	color = color*0.8;

    	vec3 hsv = hsv2rgb(vec3(color,1,1));
    	gl_FragColor = vec4( hsv , 1.0 );

    //gl_FragColor = v_color * texture2D(u_texture, v_texCoords);
}