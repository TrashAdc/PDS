#ifdef GL_ES
precision lowp float;
#endif

#extension GL_OES_standard_derivatives : enable

uniform float time;
uniform vec2 mouse;
uniform vec2 resolution;

//https://www.shadertoy.com/view/XsVSzW

void main( void )
{

	vec2 uv = ( gl_FragCoord.xy / resolution.xy )*4.0;

	float i0=1.2;
	float i1=0.95;
	float i2=1.5;
	vec2 i4=vec2(0.0,0.0);
	for(int s=0;s<4;s++)
	{
		vec2 r;
		r=vec2(cos(uv.y*i0-i4.y+time/i1),sin(uv.x*i0+i4.x+time/i1))/i2;
		r+=vec2(-r.y,r.x)*0.2;
		uv.xy+=r;

		i0*=1.93;
		i1*=1.25;
		i2*=1.7;
		i4+=r.xy*1.0+0.5*time*i1;
	}
	float r=sin(uv.x-time)*0.5+0.5;
	float b=sin(uv.y+time)*0.5+0.5;
	float g=sin((sqrt(uv.x*uv.x+uv.y*uv.y)+time))*0.5+0.5;
	vec3 c=vec3(r,g,b);
	gl_FragColor = vec4(c,1.0);
}